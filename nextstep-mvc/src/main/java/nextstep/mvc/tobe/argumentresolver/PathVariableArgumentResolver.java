package nextstep.mvc.tobe.argumentresolver;

import nextstep.utils.PathPatternUtils;
import nextstep.web.annotation.PathVariable;
import nextstep.web.support.MethodParameter;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class PathVariableArgumentResolver implements HandlerMethodArgumentResolver{

    @Override
    public boolean supports(MethodParameter parameter) {
        return parameter.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        PathPattern pathPattern = PathPatternUtils.parse(parameter.getPath());
        Map<String, String> variables = pathPattern.matchAndExtract(
                PathPatternUtils.toPathContainer(request.getRequestURI())).getUriVariables();
        return WrapperClass.parse(parameter.getParameterType(), variables.get(parameter.getParameterName()));
    }
}
