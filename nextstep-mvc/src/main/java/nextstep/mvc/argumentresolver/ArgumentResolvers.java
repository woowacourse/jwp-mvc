package nextstep.mvc.argumentresolver;

import nextstep.mvc.exception.NotFoundArgumentResolverException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentResolvers {
    private final List<ArgumentResolver> argumentResolvers;

    public ArgumentResolvers(List<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public List<Object> collectValues(HttpServletRequest request, HttpServletResponse response, List<MethodParameter> methodParameters) {
        return methodParameters.stream()
                .map(methodParameter -> resolveArgument(request, response, methodParameter))
                .collect(Collectors.toList());
    }

    private Object resolveArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        ArgumentResolver argumentResolver = getArgumentResolver(methodParameter);
        return argumentResolver.resolve(request, response, methodParameter);
    }

    private ArgumentResolver getArgumentResolver(MethodParameter methodParameter) {
        return argumentResolvers.stream()
                .filter(ar -> ar.canResolve(methodParameter))
                .findAny()
                .orElseThrow(NotFoundArgumentResolverException::new);
    }
}
