package nextstep.mvc.tobe;

import nextstep.mvc.exception.ControllerInstantiateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ControllerScannerTest {
    private ControllerScanner controllerScanner;

    @BeforeEach
    void setUp() {
        controllerScanner = new ControllerScanner("nextstep.mvc.tobe.MyController");
    }

    @Test
    @DisplayName("컨트롤러 어노테이션이 있는 클래스")
    void getControllerAnnotationClass() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        controllers.put(MyController.class, new MyController());
        assertThat(controllerScanner.getControllers()).isEqualTo(controllers.keySet());
    }

    @Test
    @DisplayName("컨트롤러 클래스에 기본 생성자가 없을 경우")
    void controllerInitError() {
        assertThrows(ControllerInstantiateException.class, () -> new ControllerScanner("nextstep.mvc.tobe.ScannerTestController"));
    }
}