package nextstep.mvc;

import nextstep.mvc.tobe.core.RequestHandlers;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestHandlers handlers;

    public DispatcherServlet(RequestHandlers handlers) {
        this.handlers = handlers;
    }

    @Override
    public void init() {
        handlers.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            ModelAndView mv = handlers.handle(req, resp);
            sendResponse(mv, req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void sendResponse(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        modelAndView.render(req, resp);
    }
}
