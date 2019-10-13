package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.NotFoundHandlerException;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.WebRequestContext;
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
        handlerMappings = Collections.unmodifiableSet((Set<HandlerMapping>) context.getBean(HandlerMapping.class));
        handlerAdapters = Collections.unmodifiableSet((Set<HandlerAdapter>) context.getBean(HandlerAdapter.class));
        viewResolvers = Collections.unmodifiableSet((Set<ViewResolver>) context.getBean(ViewResolver.class));
    }

    @Override
    public void init() {
        handlerMappings
                .forEach(handlerMapping -> handlerMapping.initialize(new AnnotationApplicationContext(context.getBasePackage())));

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        final WebRequestContext webRequest = new WebRequestContext(request, response);
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());
        try {
            doDispatch(request, response);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object handler = getHandler(request);
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        ModelAndView mv = handlerAdapter.handleInternal(handler, request, response);
        processDispatchResult(mv, request, response);
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

    private void processDispatchResult(ModelAndView mv, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        View view = resolveView(mv);
        view.render(mv.getModel(), request, response);
    }

    private View resolveView(ModelAndView mv) {
        String viewName = mv.getViewName();
        if (viewName != null) {
            return resolve(viewName);
        }
        return mv.getView();
    }

    private View resolve(String viewName) {
        return viewResolvers.stream()
                .filter(x -> x.canHandle(viewName))
                .findFirst()
                .orElseThrow()
                .resolveViewName(viewName);
    }
}
