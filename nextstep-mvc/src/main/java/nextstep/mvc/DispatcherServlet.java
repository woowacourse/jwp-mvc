package nextstep.mvc;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.WebRequestContext;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.HandlerExecutionAdapter;
import nextstep.mvc.tobe.adapter.ResponseBodyAdapter;
import nextstep.mvc.tobe.adapter.SimpleControllerAdapter;
import nextstep.mvc.tobe.exception.HandlerAdapterNotSupportedException;
import nextstep.mvc.tobe.exception.HandlerNotFoundException;
import nextstep.mvc.tobe.mapping.HandlerMapping;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.ViewResolver;
import nextstep.mvc.tobe.viewresolver.ViewResolverManager;
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
import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private ViewResolverManager viewResolverManager;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
        this.handlerAdapters = Arrays.asList(new SimpleControllerAdapter(), new ResponseBodyAdapter(), new HandlerExecutionAdapter());
        this.viewResolverManager = new ViewResolverManager();
    }

    @Override
    public void init() throws ServletException {
        if (handlerMappings.isEmpty()) {
            throw new IllegalArgumentException("HandlerMapping 은 최소한 하나는 있어야 합니다.");
        }
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            logger.debug("Method : {}, Request URI : {}", req.getMethod(), req.getRequestURI());
            final WebRequest webRequest = new WebRequestContext(req, resp);

            final Object handler = getHandler(req);

            final HandlerAdapter handlerAdapter = getHandlerAdapter(handler);

            final ModelAndView mav = handlerAdapter.handle(webRequest, handler);

            final View view = viewResolverManager.resolveView(mav.getView());

            view.render(mav.getModel(), req, resp);

        } catch (HandlerNotFoundException e) {
            logger.error("not support uri: {} ", req.getRequestURI());
            resp.sendError(SC_NOT_FOUND);
        } catch (Exception e) {
            logger.error("Exception: {}", e.getMessage());
            resp.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Object getHandler(final HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }

    private HandlerAdapter getHandlerAdapter(final Object handler) {
        return handlerAdapters.stream()
                .filter(adapter -> adapter.supports(handler))
                .findAny()
                .orElseThrow(HandlerAdapterNotSupportedException::new);
    }

    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void addHandlerAdapter(final HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    public void addViewResolver(final ViewResolver viewResolver) {
        viewResolverManager.addViewResolver(viewResolver);
    }
}
