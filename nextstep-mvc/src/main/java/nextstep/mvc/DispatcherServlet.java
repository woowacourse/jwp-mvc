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
    protected void service(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestUri = request.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        final HandlerMapping handler = getModelAndView(request, response);

        try {
            ModelAndView mav = handler.execute(request, response);
            mav.render(request, response);
        } catch (Exception e) {
            logger.info("!! ERROR : {}", e.getMessage());
        }
    }

    private HandlerMapping getModelAndView(final HttpServletRequest request, final HttpServletResponse response) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.isSupport(request))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청한 uri 을 찾을 수 없습니다."));
    }
}
