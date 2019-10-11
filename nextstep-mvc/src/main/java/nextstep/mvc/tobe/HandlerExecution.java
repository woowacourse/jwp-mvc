package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);
    private Method method;
    private Object declaredObject;

    static Map<Class<?>, Function> primitiveCast = new HashMap<>();
    static {
        primitiveCast.put(int.class, Integer::valueOf);
        primitiveCast.put(String.class, (obj)-> obj);
        primitiveCast.put(long.class, Long::valueOf);
    }

    public HandlerExecution(Method method, Object declaredObject) {
        this.method = method;
        this.declaredObject = declaredObject;

    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Object[] objects = getObjects(request, response);
        log.debug("object : {}", objects);
        return (ModelAndView) method.invoke(declaredObject, objects);
    }

    private Object[] getObjects(HttpServletRequest request, HttpServletResponse response) {
        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        Class[] classes = method.getParameterTypes();
        log.debug("Method : {} parameterNames : {}, classes : {}", method, parameterNames, classes);

        Object[] objects = new Object[classes.length];

        for (int i = 0; i < classes.length; i++) {
            if (primitiveCast.containsKey(classes[i])) {
                objects[i] = primitiveCast.get(classes[i]).apply(request.getParameter(parameterNames[i]));
                continue;
            }
            if (classes[i].equals(HttpServletRequest.class)) {
                objects[i] = request;
                continue;
            }
            if (classes[i].equals(HttpServletResponse.class)) {
                objects[i] = response;
                continue;
            }
            objects[i] = getJavaBean(request, classes[i]);
        }
        return objects;
    }

    private Object getJavaBean(HttpServletRequest request, Class aClass) {
        try {
            Object object = aClass.newInstance();
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                declaredField.set(object, request.getParameter(declaredField.getName()));
            }
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
    }

    @FunctionalInterface
    interface Function<T> {
        T apply(String t);
    }
}
