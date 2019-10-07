package nextstep.mvc.tobe.resolver;

import nextstep.utils.PathPatternUtils;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
        final Map<String, String> uriVariables = pathPattern
                .matchAndExtract(PathPatternUtils.toPathContainer(uri))
                .getUriVariables();

        return Long.parseLong(uriVariables.get(methodParameter.getName()));
    }
}
