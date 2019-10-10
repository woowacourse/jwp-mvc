package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handlermapping.HandlerMapping;
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

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final HandlerMapping rm;

    public DispatcherServlet(HandlerMapping rm) {
        this.rm = rm;
    }

    @Override
    public void init() throws ServletException {
        rm.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        log.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object object = rm.getHandler(req);
        if (object instanceof Controller) {
            log.info("legacy HandlerMapping");
            Controller controller = (Controller) object;
            try {
                String viewName = controller.execute(req, resp);
                move(viewName, req, resp);
            } catch (Throwable e) {
                log.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        } else if (object instanceof HandlerExecution) {
            log.info("new HandlerMapping");

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
