package nextstep.mvc;

import javassist.NotFoundException;
import nextstep.mvc.tobe.HandlerAdapter;
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
import java.util.List;
import java.util.Map;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private final List<HandlerMapping> handlerMappings;
    private Map<HandlerMapping, HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, Map<HandlerMapping, HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            HandlerMapping handler = getHandler(req);
            HandlerAdapter handlerAdapter = handlerAdapters.get(handler);
            Object view = handlerAdapter.handle(req, resp, handler);
            if (view instanceof ModelAndView) {
                ModelAndView modelAndView = (ModelAndView) view;
                modelAndView.getView().render(modelAndView.getModel(), req, resp);
            } else {
                move((String) view, req, resp);
            }
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private HandlerMapping getHandler(HttpServletRequest req) throws NotFoundException {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.supports(req))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("해당하는 컨트롤러를 찾을 수 없습니다."));
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
