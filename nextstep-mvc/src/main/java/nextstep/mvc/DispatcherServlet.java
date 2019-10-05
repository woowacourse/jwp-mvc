package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;

    public DispatcherServlet(final HandlerMapping... handlerMapping) {
        handlerMappings = Arrays.asList(handlerMapping);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) {
        final String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        final ModelAndView handler = getModelAndView(req, resp);

        try {
            handler.render(req, resp);
        } catch (Exception e) {
            logger.info("!! ERROR : {}", e.getMessage());
        }
    }

    private ModelAndView getModelAndView(HttpServletRequest req, HttpServletResponse resp) {
        return handlerMappings.stream()
                .map(handlerMapping -> processException(handlerMapping, req, resp))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청한 uri 을 찾을 수 없습니다."));
    }

    private ModelAndView processException(final HandlerMapping handlerMapping, final HttpServletRequest req, final HttpServletResponse resp) {
        try {
            return handlerMapping.execute(req, resp);
        } catch (Exception e) {
            return null;
        }
    }
}
