package nextstep.mvc.argumentresolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ModelAttributeArgumentResolver implements ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(RequestParamArgumentResolver.class);

    private ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public boolean canResolve(Parameter parameter) {
        Class<?> type = parameter.getType();
        return parameter.getDeclaredAnnotations().length == 0 &&
                !type.isPrimitive() && !type.isInstance(String.class) &&
                !type.isInstance(HttpServletRequest.class) && !type.isInstance(HttpServletResponse.class) &&
                !type.isInstance(HttpSession.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response,  Method method, int index) throws Exception{
        Class<?> paramType = method.getParameterTypes()[index];

        Field[] fields = paramType.getDeclaredFields();
        Object instance = paramType.getDeclaredConstructor().newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            field.set(instance, PrimitiveParser.parse(request.getParameter(field.getName()), fieldType));
        }

        return instance;
    }
}
