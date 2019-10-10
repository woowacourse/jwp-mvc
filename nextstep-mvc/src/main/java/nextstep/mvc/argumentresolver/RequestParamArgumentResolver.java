package nextstep.mvc.argumentresolver;

import nextstep.web.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestParamArgumentResolver implements ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamArgumentResolver.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public boolean canResolve(Parameter parameter) {
        return parameter.isAnnotationPresent(RequestParam.class) ||
                (parameter.getDeclaredAnnotations().length == 0 &&
                        PrimitiveParser.canParse(parameter.getType()));
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Method method, int index) {
        String parameterName = getParameterName(method, index);
        Class<?> paramType = method.getParameterTypes()[index];
        logger.debug("parameter : {} / {}", parameterName, paramType.getName());

        return PrimitiveParser.parse(request.getParameter(parameterName), paramType);
    }

    private String getParameterName(Method method, int index) {
        RequestParam requestParam = method.getParameters()[index].getDeclaredAnnotation(RequestParam.class);
        if (requestParam == null || StringUtils.isEmpty(requestParam.name())) {
            return nameDiscoverer.getParameterNames(method)[index];
        }
        return requestParam.name();
    }
}
