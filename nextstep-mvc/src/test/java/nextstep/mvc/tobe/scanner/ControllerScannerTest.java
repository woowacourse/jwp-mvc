package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.test.MyController;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    private static final String BASE_PACKAGE = "nextstep.mvc.tobe.test";

    @Test
    void scan() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Class<?>, Object> controllerInstanceMap = ControllerScanner.scan(BASE_PACKAGE);
        assertThat(controllerInstanceMap.size()).isEqualTo(1);
        assertThat(controllerInstanceMap.containsKey(MyController.class)).isTrue();
        assertThat(controllerInstanceMap.get(MyController.class)).isEqualTo(controllerInstanceMap.get(MyController.class));
    }
}