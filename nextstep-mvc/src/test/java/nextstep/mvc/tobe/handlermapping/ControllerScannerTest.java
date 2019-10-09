package nextstep.mvc.tobe.handlermapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import samples.MyUserController;
import samples.TestUserController;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

class ControllerScannerTest {

    private ControllerScanner controllerScanner;

    @BeforeEach
    void setUp() {
        Object[] basePackage = {"samples"};
        controllerScanner = new ControllerScanner(basePackage);
    }

    @Test
    void samples_패키지_Controller_스캐닝_테스트() {
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        assertThat(controllers.get(MyUserController.class).getClass()).isEqualTo(MyUserController.class);
        assertNull(controllers.get(TestUserController.class));
    }
}