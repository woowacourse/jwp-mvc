package nextstep.mvc.tobe.argumentresolver;

import com.google.common.collect.Lists;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.MethodParameter;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class HandlerMethodArgumentResolverManager {
    private static final ParameterNameDiscoverer NAME_DISCOVERER = new LocalVariableTableParameterNameDiscoverer();

    private static List<HandlerMethodArgumentResolver> resolvers = Arrays.asList(
            new ServletArgumentResolver(),
            new ModelAttributeResolver(),
            new RequestParameterResolver(),
            new RequestBodyArgumentResolver(),
            new PathVariableArgumentResolver()
    );

    public static Object[] values(Method method, HttpServletRequest request, HttpServletResponse response) {
        String[] parameterNames = NAME_DISCOVERER.getParameterNames(method);
        Parameter[] parameters = method.getParameters();
        List<Object> values = Lists.newArrayList();
        String path = method.getAnnotation(RequestMapping.class).value();

        for (int i = 0; i < parameterNames.length; i++) {
            MethodParameter parameter = new MethodParameter(parameters[i], parameterNames[i], path);
            values.add(resolve(parameter, request, response));
        }

        return values.toArray();
    }

    private static Object resolve(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return resolvers.stream()
                .filter(resolver -> resolver.supports(parameter))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .resolve(parameter, request, response);
    }
}
