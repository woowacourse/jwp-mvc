package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.MyController;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    @Test
    void scan() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Class<?>, Object> controllerInstanceMap = ControllerScanner.scan("nextstep.mvc.tobe");
        assertThat(controllerInstanceMap.size()).isEqualTo(1);
        assertThat(controllerInstanceMap.containsKey(MyController.class)).isTrue();
        assertThat(controllerInstanceMap.get(MyController.class)).isEqualTo(controllerInstanceMap.get(MyController.class));
    }
}