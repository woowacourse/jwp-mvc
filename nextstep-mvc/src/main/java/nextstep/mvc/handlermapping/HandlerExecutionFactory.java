package nextstep.mvc.handlermapping;

import nextstep.mvc.tobe.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<HandlerExecution> fromMethod(Method method) {
        if (isNotHandlerExecution(method)) {
            return Optional.empty();
        }

        return getMethodInstance(method)
                .map(instance -> ((request, response) -> {
                    try {
                        return (ModelAndView) method.invoke(instance, request, response);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("should not be called: ", e);
                        return null;
                    }
                }));
    }

    private Optional<Object> getMethodInstance(Method method) {
        try {
            return Optional.of(method.getDeclaringClass().getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("error: ", e);
            return Optional.empty();
        }
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
}
