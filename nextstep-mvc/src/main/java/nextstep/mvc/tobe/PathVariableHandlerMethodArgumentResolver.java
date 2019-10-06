package nextstep.mvc.tobe;

import nextstep.mvc.tobe.utils.PathUtils;
import nextstep.web.annotation.PathVariable;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PathVariableHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supports(final MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(PathVariable.class);
    }

    //todo 하나씩 하는데 효율이 좋지 못하다. 한 번에 하는 방법 생각
    //todo uri /users/{id} 말고도 되게 하기
    @Override
    public Object resolveArgument(final HttpServletRequest request, final MethodParameter methodParameter) {
        final String uri = request.getRequestURI();
        final PathPattern pp = PathUtils.parse("/users/{id}");
        final Map<String, String> uriVariables = pp
                .matchAndExtract(PathUtils.toPathContainer(uri))
                .getUriVariables();

        return Long.parseLong(uriVariables.get(methodParameter.getName()));
    }
}
