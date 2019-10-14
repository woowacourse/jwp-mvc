package nextstep.mvc.tobe.argumentresolver;

import nextstep.web.annotation.PathVariable;
import nextstep.web.support.MethodParameter;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

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
        PathPattern pathPattern = parse(parameter.getPath());
        Map<String, String> variables =
                pathPattern.matchAndExtract(toPathContainer(request.getRequestURI())).getUriVariables();
        return WrapperClass.parseWrapper(parameter.getParameterType(), variables.get(parameter.getParameterName()));
    }

    private PathPattern parse(String path) {
        PathPatternParser pp = new PathPatternParser();
        pp.setMatchOptionalTrailingSeparator(true);
        return pp.parse(path);
    }

    private PathContainer toPathContainer(String path) {
        if (path == null) {
            return null;
        }
        return PathContainer.parsePath(path);
    }
}
