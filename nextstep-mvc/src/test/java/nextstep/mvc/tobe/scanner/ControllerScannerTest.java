package nextstep.mvc.tobe.scanner;

import org.junit.jupiter.api.Test;
import samples.MyController;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    private Object[] basePackage = {"samples"};

    @Test
    void test() {
        Set<Class<?>> sampleControllers = ControllerScanner.scan(basePackage);
        assertThat(sampleControllers.contains(MyController.class)).isTrue();
    }

}