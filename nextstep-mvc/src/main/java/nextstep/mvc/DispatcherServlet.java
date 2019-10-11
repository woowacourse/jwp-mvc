package nextstep.mvc;

import nextstep.mvc.tobe.RequestContext;
import nextstep.mvc.tobe.argumentresolver.HandlerMethodArgumentResolver;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.returnvaluehandler.HandlerMethodReturnValueHandler;
import nextstep.mvc.tobe.view.ErrorView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.ViewResolver;
import nextstep.utils.ClassUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final Reflections reflections;
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;
    private List<HandlerMethodArgumentResolver> argumentResolvers;
    private List<HandlerMethodReturnValueHandler> returnValueHandlers;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.asList(handlerMappings);
        this.reflections = new Reflections(this.getClass().getPackage().getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
        this.argumentResolvers = (List<HandlerMethodArgumentResolver>) detectSubTypesOf(HandlerMethodArgumentResolver.class);
        this.returnValueHandlers = (List<HandlerMethodReturnValueHandler>) detectSubTypesOf(HandlerMethodReturnValueHandler.class);
        this.handlerAdapters = (List<HandlerAdapter>) detectSubTypesOf(HandlerAdapter.class);
        this.viewResolvers = (List<ViewResolver>) detectSubTypesOf(ViewResolver.class);

        addArgumentResolvers();
        addReturnTypeResolvers();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Method : {}, Request URI : {}", request.getMethod(), request.getRequestURI());
        RequestContext requestContext = new RequestContext(request, response);
        try {
            Object handler = findHandler(request);
            HandlerAdapter handlerAdapter = findHandlerAdapter(handler);
            ModelAndView modelAndView = handlerAdapter.handle(requestContext, handler);
            render(requestContext, modelAndView);
        } catch (HttpServletRequestException e) {
            View errorView = ErrorView.defaultErrorView();
            errorView.render(Collections.singletonMap("httpStatus", e.getHttpStatus()), requestContext);
        } catch (Exception e) {
            logger.error("Internal Server Exception : ", e);
        }
    }

    private void render(RequestContext requestContext, ModelAndView modelAndView) {
        if (requestContext.isHandled()) {
            return;
        }

        View view = findView(modelAndView);
        view.render(modelAndView.getModelMap(), requestContext);
    }

    private void addArgumentResolvers() {
        handlerAdapters.stream()
                .filter(HandlerAdapter::hasResolvers)
                .forEach(handlerAdapter -> handlerAdapter.addArgumentResolvers(argumentResolvers));
    }

    private void addReturnTypeResolvers() {
        handlerAdapters.stream()
                .filter(HandlerAdapter::hasResolvers)
                .forEach(handlerAdapter -> handlerAdapter.addReturnValueHandlers(returnValueHandlers));
    }

    private List<?> detectSubTypesOf(Class<?> clazz) {
        return reflections.getSubTypesOf(clazz).stream()
                .map(ClassUtils::newInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    private View findView(ModelAndView modelAndView) {
        if (modelAndView.hasViewReference()) {
            return modelAndView.getView();
        }

        String viewName = modelAndView.getViewName();
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(viewName))
                .map(viewResolver -> viewResolver.resolve(viewName))
                .findAny()
                .orElseThrow(ViewNotFoundException::new);
    }

    private HandlerAdapter findHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny()
                .orElseThrow(HandlerAdapterNotFoundException::new);
    }

    private Object findHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }
}
