package nextstep.mvc;

import nextstep.mvc.exception.ExceptionFunction;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.resolver.ViewResolver;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final String VIEW_RESOLVER_BASE_PACKAGE = "nextstep.mvc.tobe.resolver";

    private List<HandlerMapping> handlerMappings;
    private HandlerAdapter handlerAdapter;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, HandlerAdapter handlerAdapter) throws ServletException {
        this.handlerMappings = handlerMappings;
        this.handlerAdapter = handlerAdapter;
        this.viewResolvers = initViewResolvers();
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        HandlerExecution handler = getHandler(req);
        ModelAndView mav = handlerAdapter.handle(req, resp, handler);
        resolveView(mav);
        renderView(req, resp, mav);
    }

    private List<ViewResolver> initViewResolvers() {
        Reflections reflections = new Reflections(VIEW_RESOLVER_BASE_PACKAGE);
        Set<Class<? extends ViewResolver>> classes = reflections.getSubTypesOf(ViewResolver.class);

        return classes.stream()
                .map(wrapper(aClass -> aClass.getDeclaredConstructor().newInstance()))
                .collect(Collectors.toList());
    }

    private HandlerExecution getHandler(HttpServletRequest req) throws ServletException {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.supports(req))
                .findFirst()
                .orElseThrow(ServletException::new)
                .getHandler(req);
    }

    private void resolveView(ModelAndView mav) throws ServletException {
        View view = this.viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(mav))
                .findFirst()
                .orElseThrow(ServletException::new)
                .resolveViewName(mav.getViewName());
        mav.setView(view);
    }

    private void renderView(HttpServletRequest req, HttpServletResponse resp, ModelAndView mav) throws ServletException {
        try {
            mav.getView().render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    private <T, R, E extends Exception> Function<T, R> wrapper(ExceptionFunction<T, R, E> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };
    }
}
