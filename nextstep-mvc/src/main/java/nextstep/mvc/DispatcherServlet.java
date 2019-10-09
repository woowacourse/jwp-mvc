package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMappingRepository handlerMappingRepository;

    public DispatcherServlet(HandlerMappingRepository handlerMappingRepository) {
        this.handlerMappingRepository = handlerMappingRepository;
    }

    @Override
    public void init() throws ServletException {
        handlerMappingRepository.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            Object handler = findHandler(req);
            render(req, resp, handler);
        } catch (HandlerNotFoundException e) {
            logger.error("Exception : {}", e);
            resp.sendError(404);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void render(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        if (handler instanceof Controller) {
            String viewName = ((Controller) handler).execute(req, resp);
            move(viewName, req, resp);
        }
        if (handler instanceof HandlerExecution) {
            ModelAndView modelAndView = ((HandlerExecution) handler).execute(req, resp);
            modelAndView.render(req, resp);
        }
    }

    private Object findHandler(HttpServletRequest req) {
        return handlerMappingRepository.findHandler(req);
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
