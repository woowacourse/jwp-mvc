package nextstep.mvc.tobe.support;

import nextstep.mvc.tobe.MyController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerScannerTest {
    private ControllerScanner controllerScanner;

    @BeforeEach
    public void setUp()
            throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        controllerScanner = new ControllerScanner("nextstep.mvc.tobe");
    }

    @Test
    public void getControllers() {
        Set<Class<?>> expected = new HashSet<>();
        expected.add(MyController.class);
        assertThat(controllerScanner.getControllers()).isEqualTo(expected);
    }

    @Test
    public void instantiate() {
        assertThat(controllerScanner.instantiate(MyController.class).getClass()).isEqualTo(MyController.class);
    }
}