package nextstep.mvc.argumentresolver;

import nextstep.mvc.argumentresolver.support.PrimitiveValueParser;
import nextstep.web.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestParamArgumentResolver implements ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamArgumentResolver.class);

    @Override
    public boolean canResolve(MethodParameter methodParameter) {
        return methodParameter.isAnnotationPresent(RequestParam.class) ||
                (methodParameter.hasNoDeclaredAnnotation() &&
                        PrimitiveValueParser.canParse(methodParameter.getType()));
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        String parameterName = getParameterName(methodParameter);
        Class<?> paramType = methodParameter.getType();
        logger.debug("parameter : {} / {}", parameterName, paramType.getName());

        return PrimitiveValueParser.parse(request.getParameter(parameterName), paramType);
    }

    private String getParameterName(MethodParameter methodParameter) {
        RequestParam requestParam = methodParameter.getDeclaredAnnotation(RequestParam.class);
        if (requestParam == null || StringUtils.isEmpty(requestParam.name())) {
            return methodParameter.getParameterName();
        }
        return requestParam.name();
    }
}