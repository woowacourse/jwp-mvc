package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.NoHandlerMatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            ModelAndView mav = handleRequest(req, resp);
            mav.getView().render(mav.getModel(), req, resp);
        } catch (NoHandlerMatchException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param req
     * @return Controller or HandlerExecution which matches given request
     */
    private ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws RuntimeException {
        return handlerMappings.stream()
                .map(mappnig -> mappnig.getHandler(req))
                .map(handler -> handler.apply(req, resp))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new NoHandlerMatchException(req));
    }
}
