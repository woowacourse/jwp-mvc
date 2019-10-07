package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.ViewResolver;
import nextstep.utils.ClassUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
        handlerAdapters = detectHandlerAdapters();
        viewResolvers = detectViewResolvers();
    }

    private List<HandlerAdapter> detectHandlerAdapters() {
        return new Reflections(this.getClass().getPackage().getName())
                .getSubTypesOf(HandlerAdapter.class)
                .stream()
                .map(ClassUtils::createInstance)
                .map(handlerAdapter -> (HandlerAdapter) handlerAdapter)
                .collect(Collectors.toList());
    }

    private List<ViewResolver> detectViewResolvers() {
        return new Reflections(this.getClass().getPackage().getName())
                .getSubTypesOf(ViewResolver.class)
                .stream()
                .map(ClassUtils::createInstance)
                .map(viewResolver -> (ViewResolver) viewResolver)
                .collect(Collectors.toList());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            Object handler = findHandler(req);
            HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);
            View view = resolveView(modelAndView);
            view.render(modelAndView.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            e.printStackTrace();
        }
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.isSupport(handler))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private Object findHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private View resolveView(ModelAndView modelAndView) {
        return viewResolvers.stream()
                .map(viewResolver -> viewResolver.resolveViewName(modelAndView.getView()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
