package nextstep.mvc;

import nextstep.mvc.exception.HandlerAdapterNotFoundException;
import nextstep.mvc.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> requestMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> requestMappings, List<HandlerAdapter> handlerAdapters) {
        this.requestMappings = requestMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() throws ServletException {
        requestMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
            ModelAndView modelAndView = handleRequest(req, resp);
            render(modelAndView, req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void render(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        View view = modelAndView.getView();
        view.render(modelAndView.getModel(), req, resp);
    }

    private ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Object handler = findHandler(req);
        HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
        return handlerAdapter.handle(handler, req, resp);
    }

    private Object findHandler(HttpServletRequest req) {
        return requestMappings.stream()
                .map(requestMapping -> requestMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new HandlerNotFoundException(req));
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findAny()
                .orElseThrow(HandlerAdapterNotFoundException::new);
    }
}
