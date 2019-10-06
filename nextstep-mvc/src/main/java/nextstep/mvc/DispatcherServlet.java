package nextstep.mvc;

import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());

        try {
            Object handler = findHandler(req);
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            e.printStackTrace();
        }
    }

    private Object findHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
