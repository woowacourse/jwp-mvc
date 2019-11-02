package nextstep.mvc.handler.handlermapping;

import nextstep.mvc.exception.HandlerKeyUrlNotExistException;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerKeyFactoryTest {
    private static final HandlerKeyFactory factory = HandlerKeyFactory.getInstance();

    @Test
    @DisplayName("RequestMapping.value 에 값이 없는 경우")
    void fromMethod_methodWithoutUrl() {
        Method methodWithoutUrl = findMethod("methodWithoutUrl");

        assertThrows(HandlerKeyUrlNotExistException.class, () -> factory.fromMethod(methodWithoutUrl));
    }

    @Test
    @DisplayName("RequestMapping.method 에 여러 값이 있는 경우")
    void fromMethod_ofSeveralMethods() {
        Method method_GET_POST = findMethod("method_GET_POST");

        List<HandlerKey> keys = factory.fromMethod(method_GET_POST);

        assertThat(keys.contains(HandlerKey.fromUrlAndRequestMethod("url", RequestMethod.GET))).isTrue();
        assertThat(keys.contains(HandlerKey.fromUrlAndRequestMethod("url", RequestMethod.POST))).isTrue();
    }

    @Test
    @DisplayName("RequestMapping.method 가 비어있는 경우, 모든 RequestMethod 의 키 들을 생성")
    void fromMethod_ofEmptyMethods() {
        Method method_empty_method = findMethod("method_empty_method");

        List<HandlerKey> keys = factory.fromMethod(method_empty_method);

        for (RequestMethod requestMethod : RequestMethod.values()) {
            assertThat(keys.contains(HandlerKey.fromUrlAndRequestMethod("url", requestMethod))).isTrue();
        }
    }

    private Method findMethod(String methodName) {
        return Arrays.asList(Clazz.class.getDeclaredMethods()).stream()
                .filter(method -> method.getName().equals(methodName))
                .findFirst().get();
    }

    private class Clazz {
        @RequestMapping
        void methodWithoutUrl() {
        }

        @RequestMapping(value = "url", method = {RequestMethod.GET})
        void method_GET() {
        }

        @RequestMapping(value = "url", method = {RequestMethod.GET, RequestMethod.POST})
        void method_GET_POST() {
        }

        @RequestMapping(value = "url")
        void method_empty_method() {
        }
    }
}