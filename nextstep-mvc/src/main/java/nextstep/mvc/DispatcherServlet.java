package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RequestMappingHandlerMapping;
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
    private RequestMappingHandlerMapping mappings;

    public DispatcherServlet(RequestMappingHandlerMapping mappings) {
        this.mappings = mappings;
    }

    @Override
    public void init() throws ServletException {
        mappings.initialize();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            ModelAndView mv = mappings.handle(req, resp);
            move(mv, req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void move(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String viewName = mv.getView().getViewName();
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}
