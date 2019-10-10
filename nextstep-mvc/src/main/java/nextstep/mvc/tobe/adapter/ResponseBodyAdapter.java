package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.ResponseBody;

public class ResponseBodyAdapter extends AbstractRequestMappingAdapter {

    @Override
    protected ModelAndView resolveReturn(final Object result) {
        return new ModelAndView(new JsonView(result));
    }

    @Override
    public boolean supports(final Object handler) {
        return ((HandlerExecution) handler).isAnnotationPresent(ResponseBody.class);
    }
}
