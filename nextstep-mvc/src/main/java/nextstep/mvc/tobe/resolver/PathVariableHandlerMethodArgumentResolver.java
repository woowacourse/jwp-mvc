package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.WebRequest;
import nextstep.utils.PathPatternUtils;
import nextstep.utils.TypeConverter;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.Objects;

public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(final WebRequest webRequest, final MethodParameter methodParameter) {
        final String uri = webRequest.getRequestURI();
        final String path = methodParameter.getAnnotation(RequestMapping.class).value();
        final PathPattern pathPattern = PathPatternUtils.parse(path);

        final String value = Objects.requireNonNull(pathPattern
                .matchAndExtract(PathPatternUtils.toPathContainer(uri)))
                .getUriVariables()
                .get(methodParameter.getName());

        return TypeConverter.to(methodParameter.getType()).apply(value);
    }
}
