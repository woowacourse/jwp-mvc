package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private HandlerMapping rm;
    private AnnotationHandlerMapping am;

    public DispatcherServlet(HandlerMapping rm, AnnotationHandlerMapping am) {
        this.rm = rm;
        this.am = am;
    }

    @Override
    public void init() throws ServletException {
        rm.initialize();
        try {
            am.initialize();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        try {
            HandlerExecution execution = am.getHandler(req);
            try {
                ModelAndView mv = execution.handle(req, resp);
                move2(mv, req, resp);
                return;
            } catch (Throwable e) {
                logger.error("Exception : {}", e);
                throw new ServletException(e.getMessage());
            }
        } catch (NotFoundHandlerException e) {
            Controller controller = rm.getHandler(requestUri);
            try {
                String viewName = controller.execute(req, resp);
                move(viewName, req, resp);
            } catch (Throwable error) {
                logger.error("Exception : {}", error);
                throw new ServletException(e.getMessage());
            }
        }
    }

    private void move2(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mv.getView();
        view.render(mv.getModel(), req, resp);
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
