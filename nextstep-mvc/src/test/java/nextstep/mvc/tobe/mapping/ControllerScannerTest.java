package nextstep.mvc.tobe.mapping;

import nextstep.mvc.tobe.mapping.ControllerScanner;
import nextstep.web.annotation.Controller;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    private final ControllerScanner controllerScanner = new ControllerScanner("nextstep.mvc.tobe");
    final Set<Class<?>> classes = controllerScanner.getClasses();

    @Test
    void 컨트롤러_스캔_확인() {
        assertThat(classes).hasSizeGreaterThan(0);
        classes.forEach(clazz ->
                assertThat(clazz.isAnnotationPresent(Controller.class)));

    }

    @Test
    void 클래스_타입으로_인스턴스_얻기() {
        classes.forEach(clazz ->
                assertThat(controllerScanner.getInstance(clazz)).isExactlyInstanceOf(clazz));
    }
}