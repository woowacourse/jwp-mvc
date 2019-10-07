package nextstep.mvc;

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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
    }

    @Override
    public void init() throws ServletException {
        this.handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            final Object view = this.handlerMappings.stream()
                                                    .map(x -> x.getHandler(req))
                                                    .filter(Objects::nonNull)
                                                    .findAny()
                                                    .get()
                                                    .handle(req, resp);
            if (view instanceof String) {
                move((String) view, req, resp);
            } else if (view instanceof ModelAndView) {
                final ModelAndView modelAndView = (ModelAndView) view;
                modelAndView.getView().render(modelAndView.getModel(), req, resp);
            }
        } catch (Throwable e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }

    private void move(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }
        final RequestDispatcher rd = req.getRequestDispatcher(viewName);
        rd.forward(req, resp);
    }
}