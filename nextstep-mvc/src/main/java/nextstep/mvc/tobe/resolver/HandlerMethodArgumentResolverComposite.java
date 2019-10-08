package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;

import java.util.ArrayList;
import java.util.List;

public class HandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
    private final List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
    private final HandlerMethodArgumentResolver defaultResolver = new DefaultHandlerMethodArgumentResolver();

    private HandlerMethodArgumentResolverComposite() {
        resolvers.add(new PathVariableHandlerMethodArgumentResolver());
        resolvers.add(new HttpServletHandlerMethodArgumentResolver());
        resolvers.add(new RequestBodyMethodArgumentResolver());
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        final HandlerMethodArgumentResolver resolver = resolvers.stream()
                .filter(x -> x.supports(methodParameter))
                .findAny()
                .orElse(defaultResolver);

        return resolver.resolveArgument(webRequest, methodParameter);
    }

    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return true;
    }

    public void addHandlerMethodArgumentResolver(final HandlerMethodArgumentResolver resolver) {
        resolvers.add(resolver);
    }

    public static HandlerMethodArgumentResolverComposite getInstance(){
        return LazyHolder.INSTANCE;
    }

    // todo DI or Container 만들면 싱글턴 말고 빈으로 관리하기.
    // 현재는 사용 편의성을 위해서 싱글턴으로 관리함. (매번 생성해서 직접 di 해주려면 테스트가 힘들어져서)
    private static class LazyHolder {
        public static final HandlerMethodArgumentResolverComposite INSTANCE = new HandlerMethodArgumentResolverComposite();
    }
}
