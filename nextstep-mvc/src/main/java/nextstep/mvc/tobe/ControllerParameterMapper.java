package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerParameterMapper {
    private static final Logger log = LoggerFactory.getLogger(ControllerParameterMapper.class);

    private static final Map<Class<?>, Function> primitiveCast = new HashMap<>();
    static {
        primitiveCast.put(int.class, (obj) -> Integer.valueOf((String) obj));
        primitiveCast.put(String.class, String::valueOf);
        primitiveCast.put(long.class, (obj) -> Long.valueOf((String) obj));
    }

    private final Method method;

    public ControllerParameterMapper(Method method) {
        this.method = method;
    }

    public Object[] getObjects(HttpServletRequest request, HttpServletResponse response) {
        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class[] classes = method.getParameterTypes();

        log.debug("Method : {} parameterNames : {}, classes : {}", method, parameterNames, classes);

        Object[] objects = new Object[classes.length];

        for (int i = 0; i < classes.length; i++) {
            objects[i] = getObject(classes[i], parameterNames[i], request, response);
        }
        return objects;
    }

    private Object getObject(Class clazz, String parameterName, HttpServletRequest request, HttpServletResponse response) {
        if (primitiveCast.containsKey(clazz)) {
            return primitiveCast.get(clazz).apply(request.getParameter(parameterName));
        }
        if (clazz.equals(HttpServletRequest.class)) {
            return request;
        }
        if (clazz.equals(HttpServletResponse.class)) {
            return response;
        }
        return getJavaBean(request, clazz);
    }

    private Object getJavaBean(HttpServletRequest request, Class clazz) {
        try {
            Object object = clazz.newInstance();
            setParameter(request, clazz.getDeclaredFields(), object);
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("JavaBean 생성 불가");
        }
    }

    private void setParameter(HttpServletRequest request, Field[] fields, Object object) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(object, request.getParameter(field.getName()));
        }
    }

    @FunctionalInterface
    interface Function<T> {
        T apply(Object t);
    }
}