package nextstep.mvc.argumentresolver;

import nextstep.web.annotation.PathVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathVariableArgumentResolver implements ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(PathVariableArgumentResolver.class);

    @Override
    public boolean canResolve(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(PathVariable.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        String parameterName = getParameterName(methodParameter);
        Class<?> paramType = methodParameter.getType();
        logger.debug("parameter : {} / {}", parameterName, paramType.getName());

        String pathVariable = methodParameter.getPathVariable(parameterName, request.getRequestURI());
        return PrimitiveParser.parse(pathVariable, paramType);
    }

    private String getParameterName(MethodParameter methodParameter) {
        PathVariable requestParam = methodParameter.getDeclaredAnnotation(PathVariable.class);
        if (requestParam == null || StringUtils.isEmpty(requestParam.name())) {
            return methodParameter.getParameterName();
        }
        return requestParam.name();
    }
}
