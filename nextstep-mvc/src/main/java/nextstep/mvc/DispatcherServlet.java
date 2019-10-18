package nextstep.mvc;

import nextstep.mvc.exception.HandlerAdapterNotFoundException;
import nextstep.mvc.exception.HandlerNotExistException;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final List<HandlerAdapter> handlerAdapters;
    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(List<HandlerAdapter> handlerAdapters, List<HandlerMapping> handlerMappings) {
        this.handlerAdapters = handlerAdapters;
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);
        HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
        try {
            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);
            view(req, resp, modelAndView);
        } catch (Exception e) {
            logger.info("URI: {}", req.getRequestURI());
            logger.error("Exception : ", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void view(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView) throws Exception {
        ViewResolver viewResolver = new ViewResolver();
        View view = viewResolver.resolve(modelAndView.getView());
        view.render(modelAndView.getModel(), req, resp);
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
            .filter(handlerAdapter -> handlerAdapter.supports(handler))
            .findAny()
            .orElseThrow(HandlerAdapterNotFoundException::new);
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
            .map(handlerMapping -> handlerMapping.getHandler(req))
            .filter(Objects::nonNull)
            .findAny()
            .orElseThrow(HandlerNotExistException::new);
    }
}
