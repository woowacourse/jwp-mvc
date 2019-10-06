package nextstep.mvc;

import nextstep.mvc.tobe.MyController;
import nextstep.web.annotation.Controller;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationScannerTest {
    @Test
    @DisplayName("MyController scan")
    void scan() {
        Map<Class<?>, Object> mappings = AnnotationScanner.scan(Controller.class, "nextstep.mvc");

        assertThat(mappings.keySet().contains(MyController.class)).isTrue();
        assertThat(mappings.get(MyController.class)).isInstanceOf(MyController.class);
    }
}
