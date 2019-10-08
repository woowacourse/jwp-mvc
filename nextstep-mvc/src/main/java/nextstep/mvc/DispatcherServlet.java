package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.*;
import nextstep.mvc.tobe.handlerAdapter.HandlerAdapter;
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
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() {
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);
        try {
            HandlerExecution execution = am.getHandler(req);
            ModelAndView mv = execution.handle(req, resp);
            move2(mv, req, resp);

        } catch (NotFoundHandlerException | IllegalAccessException | InvocationTargetException e) {
            Controller controller = (Controller) rm.getHandler();
            try {
                String viewName = controller.execute(req, resp);
                move(viewName, req, resp);
            } catch (Throwable error) {
                logger.error("Exception : {}", error);
                throw new ServletException(e.getMessage());
            }
        }
    }

    private void move2(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
