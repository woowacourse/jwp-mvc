package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.tobe.argumentresolver.exception.ArgumentResolveFailedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class ArgumentResolvers {
    private final Set<ArgumentResolver> argumentResolvers;

    public ArgumentResolvers(Set<ArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public Object[] resolve(MethodParameters methodParameters, HttpServletRequest req, HttpServletResponse resp) {
        return methodParameters.stream()
                .map(param -> resolveArgument(param, req, resp))
                .toArray();
    }

    private Object resolveArgument(MethodParameter methodParameter, HttpServletRequest req, HttpServletResponse resp) {
        return argumentResolvers.stream()
                .filter(argumentResolver -> argumentResolver.supports(methodParameter))
                .map(argumentResolver -> argumentResolver.resolve(req, resp, methodParameter))
                .findAny()
                .orElseThrow(ArgumentResolveFailedException::new);
    }
}
