package nextstep.mvc;

import nextstep.mvc.exception.NotFoundHandlerAdapterException;
import nextstep.mvc.exception.NotFoundHandlerException;
import nextstep.mvc.handleradapter.AnnotationHandlerAdapter;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handleradapter.LegacyHandlerAdapter;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    @Override
    public void init() throws ServletException {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
        handlerAdapters = Arrays.asList(new LegacyHandlerAdapter(), new AnnotationHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        Object handler = getHandler(req);
        try {
            HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(req, resp, handler);
            modelAndView.getView().render(modelAndView.getModel(), req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resp.sendError(404);
        }
    }

    private Object getHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.canHandle(req))
                .findAny()
                .orElseThrow(NotFoundHandlerException::new)
                .getHandler(req);
    }

    private HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(adapter -> adapter.canHandle(handler))
                .findAny()
                .orElseThrow(NotFoundHandlerAdapterException::new);
    }
}
