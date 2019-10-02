package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.HandlerExecution;
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

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping rm;
    private AnnotationHandlerMapping arm;

    public DispatcherServlet(HandlerMapping rm) {
        this.rm = rm;
    }

    @Override
    public void init() throws ServletException {
        arm = new AnnotationHandlerMapping("slipp.controller");
        rm.initialize();
        arm.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Controller controller = rm.getHandler(requestUri);

        if (controller == null) {
               HandlerExecution handlerExecution = arm.getHandler(req);
            try {
                ModelAndView modelAndView = handlerExecution.handle(req, resp);
                String name = modelAndView.getViewName();
                move(name, req, resp);
            } catch (Exception e) {
                logger.error("Exception : {}", e.getMessage());
                resp.setStatus(404);
            }
        } else {
            try {
                String viewName = controller.execute(req, resp);
                move(viewName, req, resp);
            } catch (Throwable e) {
                logger.error("Exception : {}", e.getMessage());
                throw new ServletException(e.getMessage());
            }
        }
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
