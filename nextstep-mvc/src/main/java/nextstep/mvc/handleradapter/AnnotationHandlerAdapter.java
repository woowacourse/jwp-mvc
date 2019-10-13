package nextstep.mvc.handleradapter;

import nextstep.mvc.argumentresolver.ArgumentResolvers;
import nextstep.mvc.argumentresolver.MethodParameter;
import nextstep.mvc.handlermapping.HandlerExecution;
import nextstep.mvc.modelandview.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AnnotationHandlerAdapter implements HandlerAdapter {
    private ArgumentResolvers argumentResolvers;

    @Override
    public boolean canHandle(Object handler) {
        return handler instanceof HandlerExecution;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerExecution castHandler = (HandlerExecution) handler;
        List<MethodParameter> methodParameters = castHandler.extractMethodParameters();

        List<Object> values = argumentResolvers.collectValues(request, response, methodParameters);
        Object result = castHandler.handle(values.toArray());

        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }
        String viewName = (String) result;
        return new ModelAndView(viewName);
    }

    @Override
    public void addArugmentResolvers(ArgumentResolvers argumentResolvers1) {
        argumentResolvers = argumentResolvers1;
    }
}