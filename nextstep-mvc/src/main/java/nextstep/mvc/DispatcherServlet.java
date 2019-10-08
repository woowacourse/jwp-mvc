package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.View;
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

    public DispatcherServlet() {
        handlerMappings = new ArrayList<>();
    }

    public void addHandlerMapping(HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        Optional<HandlerExecution> handlerExecution = findHandlerExecution(req);

        if (handlerExecution.isPresent()) {
            renderByHandler(req, resp, handlerExecution.get());
            return;
        }
    }

    private Optional<HandlerExecution> findHandlerExecution(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handler -> handler.containsKey(req))
                .map(handler -> handler.getHandler(req))
                .findFirst();
    }

    private void renderByHandler(HttpServletRequest req, HttpServletResponse resp, HandlerExecution handler) throws ServletException {
        try {
            ModelAndView mav = handler.handle(req, resp);
            View view = mav.getView();

            if (view != null) {
                view.render(mav.getModel(), req, resp);
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new ServletException();
        }
    }
}
