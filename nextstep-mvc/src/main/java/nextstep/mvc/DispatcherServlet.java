package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
import nextstep.mvc.tobe.handlerAdapter.HandlerAdapter;
import nextstep.mvc.tobe.handlerAdapter.exception.NotFoundHandlerAdapterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final long serialVersionUID = 1L;

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
        handlerAdapters = new ArrayList<>();
    }

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        Optional<Object> handler = findHandler(req);

        if (handler.isPresent()) {
            renderByHandler(req, resp, handler.get());
            return;
        }
    }

    private Optional<Object> findHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handler -> handler.containsKey(req))
                .map(handler -> handler.getHandler(req))
                .findFirst();
    }

    private void renderByHandler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException {
        try {
            HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
            ModelAndView mav = handlerAdapter.handle(req, resp, handler);
            View view = mav.getView();

            if (view != null) {
                view.render(mav.getModel(), req, resp);
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new ServletException();
        }
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(NotFoundHandlerAdapterException::new);
    }
}
