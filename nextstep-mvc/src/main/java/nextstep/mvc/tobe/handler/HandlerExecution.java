package nextstep.mvc.tobe.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HandlerExecution {
    private final Object instance;
    private final Method method;

    public HandlerExecution(final Object instance, final Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object execute(Object... objects) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(instance, objects);
    }

    public Parameter[] getParameters() {
        return method.getParameters();
    }

    public Method getMethod() {
        return method;
    }

    public boolean isAnnotationPresent(final Class<? extends Annotation> annotation){
        return method.isAnnotationPresent(annotation);
    }
}
