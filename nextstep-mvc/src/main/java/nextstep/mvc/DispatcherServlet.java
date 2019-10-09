package nextstep.mvc;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.exception.NotFoundHandlerException;
import nextstep.mvc.tobe.exception.NotSupportHandlerAdapterException;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final List<HandlerMapping> handlerMappings;
    private final List<HandlerAdapter> handlerAdapters;

    public DispatcherServlet(final List<HandlerMapping> handlerMappings, final List<HandlerAdapter> handlerAdapters) {
        this.handlerMappings = new ArrayList<>(handlerMappings);
        this.handlerAdapters = new ArrayList<>(handlerAdapters);
    }

    @Override
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response) {
        final String requestUri = request.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), requestUri);

        final Object handler = getHandler(request);
        final HandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        try {
            final ModelAndView mav = handlerAdapter.handle(request, response, handler);
            mav.render(request, response);
        } catch (Exception e) {
            logger.info("!! ERROR : {}", e.getMessage());
        }
    }

    private Object getHandler(final HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new NotFoundHandlerException("지원하지 않는 URI"));
    }

    private HandlerAdapter getHandlerAdapter(final Object handler) {
        return handlerAdapters.stream()
                .filter(ha -> ha.supports(handler))
                .findAny()
                .orElseThrow(() -> new NotSupportHandlerAdapterException("지원하지 않는 handler adapter"));
    }
}
