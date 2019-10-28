package nextstep.mvc.handlermapping;

import nextstep.mvc.exception.MethodInvokeFailException;
import nextstep.mvc.exception.NextstepMvcException;
import nextstep.mvc.exception.WrongConstructorOfDeclaringClassException;
import nextstep.mvc.exception.WrongHandlerExecutionParameterTypeException;
import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HandlerExecutionFactory {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecutionFactory.class);

    private static final Class<?>[] handlerExecutionParameterTypes = {HttpServletRequest.class, HttpServletResponse.class};

    private HandlerExecutionFactory() {
    }

    private static class SingletonHolder {
        private static final HandlerExecutionFactory INSTANCE = new HandlerExecutionFactory();
    }

    public static HandlerExecutionFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public HandlerExecution fromMethod(Method method) {
        if (isNotHandlerExecution(method)) {
            throw NextstepMvcException.ofException(WrongHandlerExecutionParameterTypeException.ofMethod(method));
        }
        MethodWrapper methodWrapper = new MethodWrapperImpl(method);

        return (request, response)
                -> (ModelAndView) methodWrapper.invoke(methodWrapper.newInstanceOfDeclaringClass(), request, response);
    }

    private boolean isNotHandlerExecution(Method method) {
        return !isHandlerExecution(method);
    }

    // [TODO] HandlerExecution 에 대한 의존을 어떻게 제거할까?
    private boolean isHandlerExecution(Method method) {
        if (method.getReturnType() != ModelAndView.class) {
            return false;
        }
        return Objects.deepEquals(method.getParameterTypes(), handlerExecutionParameterTypes);
    }

    private interface MethodWrapper {
        Object newInstanceOfDeclaringClass();

        Object invoke(Object obj, Object... args);
    }

    private class MethodWrapperImpl implements MethodWrapper {
        private final Method method;

        public MethodWrapperImpl(Method method) {
            this.method = method;
        }

        @Override
        public Object newInstanceOfDeclaringClass() {
            try {
                return method.getDeclaringClass().getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error("error: ", e);
                throw WrongConstructorOfDeclaringClassException.from(e, method);
            }
        }

        @Override
        public Object invoke(Object obj, Object... args) {
            try {
                return method.invoke(obj, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("should not be called: ", e);
                throw MethodInvokeFailException.ofException(e);
            }
        }
    }
}
