package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.WebRequest;
import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.mvc.tobe.resolver.HandlerMethodArgumentResolverComposite;
import nextstep.mvc.tobe.resolver.MethodParameters;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.ResponseBody;

public class ResponseBodyAdapter implements HandlerAdapter {
    private HandlerMethodArgumentResolverComposite argumentResolvers;

    public ResponseBodyAdapter() {
        argumentResolvers = HandlerMethodArgumentResolverComposite.getInstance();
    }

    // todo 추상화로 중복 제거 or 합쳐야하나?
    @Override
    public ModelAndView handle(final WebRequest webRequest, final Object handler) throws Exception {
        final HandlerExecution handlerExecution = (HandlerExecution) handler;
        final MethodParameters methodParameters = new MethodParameters(handlerExecution.getMethod());

        final Object[] values = methodParameters.getMethodParams().stream()
                .map(parameter -> argumentResolvers.resolveArgument(webRequest, parameter))
                .toArray();

        final Object result = handlerExecution.execute(values);

        return new ModelAndView(new JsonView(result));
    }

    @Override
    public boolean supports(final Object handler) {
        return ((HandlerExecution) handler).isAnnotationPresent(ResponseBody.class);
    }
}
