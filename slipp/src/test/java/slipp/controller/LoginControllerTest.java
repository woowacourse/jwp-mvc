package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserLoginDto;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

public class LoginControllerTest extends BaseControllerTest {
    @BeforeEach
    void setUp() {
        UserCreatedDto createdDto = new UserCreatedDto(
                "login",
                "password",
                "포비",
                "pobi@nextstep.camp"
        );
        client.createResource("/api/users", createdDto, UserCreatedDto.class);
    }

    @Test
    @DisplayName("사용자 로그인")
    void login() {
        UserLoginDto loginDto = new UserLoginDto("login", "password");

        client.post("/users/login")
                .body(fromFormData("userId", loginDto.getUserId())
                        .with("password", loginDto.getPassword()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/");
    }

    @Test
    @DisplayName("아이디가 틀린 경우 로그인 실패")
    void failLogin_WrongId() {
        UserLoginDto loginDto = new UserLoginDto("notExist", "password");

        client.post("/users/login")
                .body(fromFormData("userId", loginDto.getUserId())
                        .with("password", loginDto.getPassword()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/user/login");
    }

    @Test
    @DisplayName("비밀번호가 틀린 경우 로그인 실패")
    void failLogin_WrongPassword() {
        UserLoginDto loginDto = new UserLoginDto("login", "wrong");

        client.post("/users/login")
                .body(fromFormData("userId", loginDto.getUserId())
                        .with("password", loginDto.getPassword()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/user/login");
    }
}
