package nextstep.utils;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.utils.exception.ClassToInstanceFailedException;
import org.junit.jupiter.api.Test;
import slipp.controller.LoginController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClassUtilsTest {
    @Test
    void classToInstanceTest_성공() {
        assertThat(ClassUtils.classToInstance(LoginController.class)).isInstanceOf(LoginController.class);
    }

    @Test
    void classToInstanceTest_실패() {
        assertThrows(ClassToInstanceFailedException.class, () ->
                ClassUtils.classToInstance(HandlerExecution.class));
    }

}