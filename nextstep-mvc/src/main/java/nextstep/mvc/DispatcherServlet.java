package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.exception.HandlerNotExistException;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.UnsupportedHandlerTypeException;
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

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() throws ServletException {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        HandlerExecution handler = getHandler(req);

        try {
            handle(req, resp, handler);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private HandlerExecution getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
            .filter(handlerMapping -> handlerMapping.getHandler(req) != null)
            .findFirst()
            .orElseThrow(HandlerNotExistException::new)
            .getHandler(req);
    }

    private void handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (handler instanceof Controller) {
            handleLegacy(req, resp, (Controller) handler);
            return;
        }

        if (handler instanceof HandlerExecution) {
            handleAnnotation(req, resp, (HandlerExecution) handler);
            return;
        }

        throw new UnsupportedHandlerTypeException();
    }

    private void handleAnnotation(HttpServletRequest req, HttpServletResponse resp, HandlerExecution handler) throws Exception {
        ModelAndView view = handler.handle(req, resp);
        view.render(req, resp);
    }

    private void handleLegacy(HttpServletRequest req, HttpServletResponse resp, Controller handler) throws Exception {
        String viewName = handler.execute(req, resp);
        move(viewName, req, resp);
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
