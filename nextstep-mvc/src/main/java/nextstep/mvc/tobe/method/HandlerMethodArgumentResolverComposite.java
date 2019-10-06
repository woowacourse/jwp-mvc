package nextstep.mvc.tobe.method;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
    private final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
    private final HandlerMethodArgumentResolver defaultResolver = new DefaultHandlerMethodArgumentResolver();

    public HandlerMethodArgumentResolverComposite() {
        resolvers.add(new PathVariableHandlerMethodArgumentResolver());

    }

    @Override
    public Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter, final Method method) {
        final HandlerMethodArgumentResolver resolver = resolvers.stream()
                .filter(x -> x.supports(methodParameter))
                .findAny()
                .orElse(defaultResolver);

        return resolver.resolveArgument(request, methodParameter, method);
    }

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return true;
    }

    public void addHandlerMethodArgumentResolver(final HandlerMethodArgumentResolver resolver) {
        resolvers.add(resolver);
    }
}
