package nextstep.mvc.tobe.interceptor;

import nextstep.utils.PathPatternUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterceptorRegistration {
    private final HandlerInterceptor interceptor;
    private List<String> includePatters = new ArrayList<>();
    private List<String> excludePatterns = new ArrayList<>();

    private InterceptorRegistration(final HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public static InterceptorRegistration from(final HandlerInterceptor interceptor) {
        return new InterceptorRegistration(interceptor);
    }

    public InterceptorRegistration addPathPatterns(final String... patterns) {
        return addPathPatterns(Arrays.asList(patterns));
    }

    public InterceptorRegistration addPathPatterns(final List<String> patterns) {
        includePatters.addAll(patterns);
        return this;
    }

    public InterceptorRegistration excludePathPatterns(final String... patterns) {
        return excludePathPatterns(Arrays.asList(patterns));
    }

    public InterceptorRegistration excludePathPatterns(final List<String> patterns) {
        excludePatterns.addAll(patterns);
        return this;
    }

    public boolean match(final String path) {
        final boolean isNotIncludePath = includePatters.stream()
                .noneMatch(pattern -> PathPatternUtils.match(pattern, path));

        if (isNotIncludePath) {
            return false;
        }
        return excludePatterns.stream()
                .noneMatch(pattern -> PathPatternUtils.match(pattern, path));
    }

    public HandlerInterceptor getInterceptor(){
        return interceptor;
    }
}
