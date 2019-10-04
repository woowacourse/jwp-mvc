package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private HandlerMapping[] rm;

    public DispatcherServlet(HandlerMapping... rm) {
        this.rm = rm;
    }

    @Override
    public void init() throws ServletException {
        Arrays.stream(rm)
                .forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        for (HandlerMapping handlerMapping : rm) {
            Object handler = handlerMapping.getHandler(req);
            if (handler instanceof Controller) {
                Controller controller = (Controller) handler;

                try {
                    String viewName = controller.execute(req, resp);
                    move(viewName, req, resp);
                    return;
                } catch (Throwable e) {
                    logger.error("Exception : {}", e);
                    throw new ServletException(e.getMessage());
                }
            } else if (handler instanceof HandlerExecution) {
                HandlerExecution execution = (HandlerExecution) handler;

                try {
                    execution.handle(req, resp);
                    return;
                } catch (Exception e) {
                    logger.error("Exception : {}", e);
                    throw new ServletException(e.getMessage());
                }
            }
        }

        throw new IllegalArgumentException();
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
