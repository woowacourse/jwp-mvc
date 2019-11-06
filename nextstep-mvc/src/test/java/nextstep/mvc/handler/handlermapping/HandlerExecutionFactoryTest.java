package nextstep.mvc.handler.handlermapping;

import nextstep.mvc.exception.WrongHandlerExecutionParameterTypeException;
import nextstep.mvc.tobe.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class HandlerExecutionFactoryTest {
    public static final ModelAndView factoryModelAndView = mock(ModelAndView.class);

    private static final HandlerExecutionFactory factory = HandlerExecutionFactory.getInstance();

    @Test
    @DisplayName("HandlerExecution 과 리턴값이 다른 메소드")
    void fromMethod_methodOfWrongReturnType() throws NoSuchMethodException {
        Method methodOfWrongReturnType = findMethod("methodOfWrongReturnType");

        assertThrows(WrongHandlerExecutionParameterTypeException.class, () -> factory.fromMethod(methodOfWrongReturnType));
    }

    @Test
    @DisplayName("HandlerExecution 과 파라미터가 다른 메소드")
    void fromMethod_methodOfWrongParams() {
        Method methodOfWrongParams = findMethod("methodOfWrongParams");

        assertThrows(WrongHandlerExecutionParameterTypeException.class, () -> factory.fromMethod(methodOfWrongParams));
    }

    @Test
    @DisplayName("HandlerExecution 과 동일한 메소드 시그니처")
    void fromMethod_correctMethod() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method correctMethod = findMethod("correctMethod");

        assertThat(factory.fromMethod(correctMethod) instanceof HandlerExecution).isTrue();
    }

    @Test
    @DisplayName("HandlerExecution 실행을 통해 해당 핸들러를 실행할 수 있는지 검증하기")
    void fromMethod_returnCorrectHandlerExecution() throws NoSuchMethodException {
        HandlerExecution execution = factory.fromMethod(
                AnnotatedController.class.getMethod("handler", HttpServletRequest.class, HttpServletResponse.class));

        assertThat(execution.handle(mock(HttpServletRequest.class), mock(HttpServletResponse.class)))
                .isEqualTo(factoryModelAndView);
    }

    private Method findMethod(String methodName) {
        return Arrays.asList(Clazz.class.getDeclaredMethods()).stream()
                .filter(method -> method.getName().equals(methodName))
                .findFirst().get();
    }
}