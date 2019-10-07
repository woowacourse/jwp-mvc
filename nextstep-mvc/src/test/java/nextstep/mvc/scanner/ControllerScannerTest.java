package nextstep.mvc.scanner;

import nextstep.web.annotation.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {

    private ControllerScanner controllerScanner;

    @BeforeEach
    void setUp() {
        controllerScanner = new ControllerScanner("mvc.exceptions");
    }

    @Test
    void getInstance() {
        assertThat(controllerScanner.getInstance(Controller.class)).isNotNull();
    }
}