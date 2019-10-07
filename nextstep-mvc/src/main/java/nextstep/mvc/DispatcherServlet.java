package nextstep.mvc;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerMappingManager;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.exception.BadRequestException;
import nextstep.mvc.tobe.handlermapping.HandlerExecution;
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
import java.util.Map;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final HandlerMappingManager handlerMappingManager;

    public DispatcherServlet(HandlerMapping... rm) {
        handlerMappingManager = new HandlerMappingManager(Arrays.asList(rm));
    }

    @Override
    public void init() throws ServletException {
        handlerMappingManager.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Handler handler = handlerMappingManager.getHandler(req);
        try {
            if (handler instanceof Controller) {
                String viewName = ((Controller) handler).handle(req, resp);
                move(viewName, req, resp);
            } else if (handler instanceof HandlerExecution) {
                ModelAndView mav = ((HandlerExecution) handler).handle(req, resp);
                render(mav, req, resp);
            } else {
                logger.error("적절하지 않은 요청입니다. {}", req);
                throw new BadRequestException();
            }
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
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

    private void render(ModelAndView mav, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = mav.getView();
        Map<String, Object> model = mav.getModel();

        view.render(model, req, resp);
    }
}
