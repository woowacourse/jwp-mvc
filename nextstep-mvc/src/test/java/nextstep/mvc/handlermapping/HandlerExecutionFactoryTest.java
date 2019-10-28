package nextstep.mvc.handlermapping;

import nextstep.mvc.exception.NextstepMvcException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerExecutionFactoryTest {
    private static final HandlerExecutionFactory factory = HandlerExecutionFactory.getInstance();

    @Test
    @DisplayName("HandlerExecution 과 리턴값이 다른 메소드")
    void fromMethod_methodOfWrongReturnType() throws NoSuchMethodException {
        Method methodOfWrongReturnType = findMethod("methodOfWrongReturnType");

        assertThrows(NextstepMvcException.class, () -> factory.fromMethod(methodOfWrongReturnType));
    }

    @Test
    @DisplayName("HandlerExecution 과 파라미터가 다른 메소드")
    void fromMethod_methodOfWrongParams() {
        Method methodOfWrongParams = findMethod("methodOfWrongParams");

        assertThrows(NextstepMvcException.class, () -> factory.fromMethod(methodOfWrongParams));
    }

    @Test
    @DisplayName("HandlerExecution 과 동일한 메소드 시그니처")
    void fromMethod_correctMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method correctMethod = findMethod("correctMethod");

        assertThat(factory.fromMethod(correctMethod) instanceof HandlerExecution).isTrue();
    }

    private Method findMethod(String methodName) {
        return Arrays.asList(Clazz.class.getDeclaredMethods()).stream()
                .filter(method -> method.getName().equals(methodName))
                .findFirst().get();
    }
}