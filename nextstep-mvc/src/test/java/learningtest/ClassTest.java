package learningtest;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassTest {
    private interface Controller {
    }

    private class ControllerImpl implements Controller {
    }

    @Test
    void isAssignableFrom() {
        assertThat(Controller.class.isAssignableFrom(ControllerImpl.class)).isTrue();
    }
}
