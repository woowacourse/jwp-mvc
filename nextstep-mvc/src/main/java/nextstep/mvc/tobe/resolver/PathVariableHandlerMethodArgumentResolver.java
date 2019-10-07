package nextstep.mvc.tobe.resolver;

import nextstep.utils.PathPatternUtils;
import nextstep.utils.TypeConverter;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter) {
        final String uri = request.getRequestURI();
        final String path = methodParameter.getMethod().getAnnotation(RequestMapping.class).value();
        final PathPattern pathPattern = PathPatternUtils.parse(path);

        final String value = Objects.requireNonNull(pathPattern
                .matchAndExtract(PathPatternUtils.toPathContainer(uri)))
                .getUriVariables()
                .get(methodParameter.getName());

        return TypeConverter.to(methodParameter.getType()).apply(value);
    }
}
