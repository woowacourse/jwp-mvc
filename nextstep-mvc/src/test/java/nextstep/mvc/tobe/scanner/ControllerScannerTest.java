package nextstep.mvc.tobe.scanner;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ControllerScannerTest {
    @Test
    @DisplayName("base pakage의 하위 클래스들을 scan하여 controller list를 반환한다.")
    void scanController() {
        List<Object> controllers = ControllerScanner.scanControllers("nextstep.mvc.tobe");

        assertThat(controllers.size()).isEqualTo(1);
    }
}