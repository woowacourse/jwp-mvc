package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.NotFoundHandlerException;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.handlerAdapter.HandlerAdapter;
import nextstep.mvc.tobe.support.AnnotationApplicationContext;
import nextstep.mvc.tobe.support.ApplicationContext;
import nextstep.mvc.tobe.view.resolver.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Set<HandlerMapping> handlerMappings;
    private Set<HandlerAdapter> handlerAdapters;
    private Set<ViewResolver> viewResolvers;
    private ApplicationContext context;

    public DispatcherServlet(ApplicationContext context) {
        this.context = context;
        context.scanBeans(HandlerAdapter.class, HandlerMapping.class, ViewResolver.class);
        handlerAdapters = Collections.unmodifiableSet((Set<HandlerAdapter>) context.getBean(HandlerAdapter.class));
        handlerMappings = Collections.unmodifiableSet((Set<HandlerMapping>) context.getBean(HandlerMapping.class));
        viewResolvers = Collections.unmodifiableSet((Set<ViewResolver>) context.getBean(ViewResolver.class));
    }

    @Override
    public void init() {
        handlerMappings
                .forEach(handlerMapping -> handlerMapping.initialize(new AnnotationApplicationContext(context.getBasePackage())));

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String requestUri = request.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        try {
            Object handler = getHandler(request);

            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            ModelAndView mv = handlerAdapter.handleInternal(handler, request, response);
            move(mv, request, response);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) {

    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(adapter -> adapter.canAdapt(handler))
                .findFirst()
                .orElseThrow(NotFoundAdapterException::new);
    }

    private Object getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(NotFoundHandlerException::new);
    }

    private void move(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        View view = mv.getView();
        view.render(mv.getModel(), request, response);
    }
}
