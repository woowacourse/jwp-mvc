package nextstep.mvc;

import com.google.common.collect.Lists;
import nextstep.mvc.asis.ControllerHandlerAdapter;
import nextstep.mvc.tobe.HandlerExecutionHandlerAdapter;
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
        HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        try {
            String viewName = (String) handlerAdapter.handle(req, resp, handler);
            move(viewName, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private Object findHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        if (new ControllerHandlerAdapter().supports(handler)) {
            return new ControllerHandlerAdapter();
        }

        if (new HandlerExecutionHandlerAdapter().supports(handler)) {
            return new HandlerExecutionHandlerAdapter();
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
