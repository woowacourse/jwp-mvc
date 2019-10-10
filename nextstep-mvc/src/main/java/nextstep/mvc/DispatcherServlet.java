package nextstep.mvc;

import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.HandlerMapping;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(List<HandlerMapping> handlerMappings, List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = handlerMappings;
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        HandlerMapping handlerMapping = findHandlerMapping(req);
        Object handler = handlerMapping.getHandler(req);
        HandlerAdapter handlerAdapter = findAdapter(handler);

        try {
            ModelAndView mav = handlerAdapter.handle(req, resp, handler);
            mav.getView().render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.debug("렌더링 실패");
        }
    }

    private HandlerMapping findHandlerMapping(HttpServletRequest req) {
        return handlerMappings.stream()
                .filter(handlerMapping -> Objects.nonNull(handlerMapping.getHandler(req)))
                .findFirst().orElseThrow(NotSupportedHandlerMethod::new);
    }

    private HandlerAdapter findAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findFirst()
                .orElseThrow(NotSupportedHandlerMethod::new);
    }
}
