package slipp.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.dto.UserRequestLoginDto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginServiceTest {
    private LoginService loginService = new LoginService();

    @Test
    @DisplayName("요청한 아이디와 패스워드가 일치하는지 확인한다.")
    void checkLogin() {
        UserRequestLoginDto loginDto = new UserRequestLoginDto("admin", "password");

        assertTrue(loginService.matchLoginData(loginDto));
    }

    @Test
    @DisplayName("비밀번호가 틀린 경우 false를 반환한다.")
    void 비밀번호로_인한_로그인_실패() {
        UserRequestLoginDto loginDto = new UserRequestLoginDto("admin", "password2");

        assertFalse(loginService.matchLoginData(loginDto));
    }

    @Test
    @DisplayName("존재하지 않는 ID로 로그인 요청을 하는 경우 false를 반환한다.")
    void 존재하지_않는_ID_로그인_실패() {
        UserRequestLoginDto loginDto = new UserRequestLoginDto("testId", "password");

        assertFalse(loginService.matchLoginData(loginDto));
    }
}