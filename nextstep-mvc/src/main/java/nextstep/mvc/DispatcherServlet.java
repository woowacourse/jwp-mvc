package nextstep.mvc;

import com.google.common.collect.Lists;
import nextstep.mvc.asis.Controller;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping... rm) {
        handlerMappings = Lists.newArrayList();
        handlerMappings.addAll(Arrays.asList(rm));
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        Object handler = findHandler(req);
        try {
            if (handler instanceof Controller) {
                ModelAndView mav = (ModelAndView) ((Controller) handler).execute(req, resp);
                mav.getView().render(mav.getModel(), req, resp);
            }

            if (handler instanceof HandlerExecution) {
                ModelAndView mav = ((HandlerExecution) handler).handle(req, resp);
                mav.getView().render(mav.getModel(), req, resp);
            }
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object execution = handlerMapping.getHandler(request);
            if (Objects.nonNull(execution)) {
                return execution;
            }
        }

        throw new IllegalArgumentException();
    }

    private void move(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (mv.getViewName().startsWith(DEFAULT_REDIRECT_PREFIX)) {
            resp.sendRedirect(mv.getViewName().substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher(mv.getViewName());
        rd.forward(req, resp);
    }
}
