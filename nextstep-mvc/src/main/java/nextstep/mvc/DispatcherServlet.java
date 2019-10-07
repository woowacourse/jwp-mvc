package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.HandlerAdapterNotFoundException;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.HandlerNotExistException;
import nextstep.mvc.tobe.ModelAndView;
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
            ModelAndView view = handlerAdapter.handle(req, resp, handler);
            view.render(req,resp);

        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
            .filter(handlerAdapter -> handlerAdapter.supports(handler))
            .findFirst()
            .orElseThrow(HandlerAdapterNotFoundException::new);
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
            .map(handlerMapping -> handlerMapping.getHandler(req))
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(HandlerNotExistException::new);
    }
}
