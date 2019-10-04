package nextstep.mvc.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    private ControllerScanner controllerScanner;

    @BeforeEach
    public void setup() {
        controllerScanner = new ControllerScanner();
        controllerScanner.scan("nextstep.mvc.tobe");
    }

    @Test
    void scan() {
        assertThat(controllerScanner.get(MyController.class)).isNotNull();
        assertThat(controllerScanner.get(ControllerScannerTest.class)).isNull();
    }

    @Test
    void getAllDeclaredMethods() {
        List<Method> methods = controllerScanner.getAllDeclaredMethods();
        assertThat(methods.size()).isEqualTo(2);

        List<String> methodNames = methods.stream()
                .map(Method::getName)
                .collect(Collectors.toList());
        assertThat(methodNames.contains("save")).isTrue();
        assertThat(methodNames.contains("findUserId")).isTrue();
    }
}