package nextstep.mvc;

import javassist.NotFoundException;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.NotFoundAdapterException;
import nextstep.mvc.tobe.NotFoundControllerException;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.NotFoundViewResolverException;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;
    private final List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters, List<ViewResolver> viewResolvers) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
        this.viewResolvers = viewResolvers;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            Object handler = findHandler(req);
            HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
            ModelAndView mv = handlerAdapter.handle(req, resp, handler);

            ViewResolver viewResolver = findViewResolver(mv);
            View view = viewResolver.resolve(mv);
            view.render(mv.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest req) throws NotFoundException {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NotFoundControllerException("해당하는 Handler를 찾을 수 없습니다."));
    }

    private HandlerAdapter findHandlerAdapter(Object handler) throws NotFoundAdapterException {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> new NotFoundAdapterException("해당하는 HandlerAdapter를 찾을 수 없습니다."));
    }

    private ViewResolver findViewResolver(ModelAndView mv) throws NotFoundViewResolverException {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(mv))
                .findFirst()
                .orElseThrow(() -> new NotFoundViewResolverException("해당하는 ViewResolver가 없습니다."));
    }
}
