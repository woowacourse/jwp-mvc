package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.handler.HandlerExecution;

public class HandlerExecutionAdapter extends AbstractRequestMappingAdapter {

    @Override
    protected ModelAndView resolveReturn(final Object result) {
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }

    @Override
    public boolean supports(final Object handler) {
        return handler instanceof HandlerExecution;
    }
}