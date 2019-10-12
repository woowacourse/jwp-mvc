package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

public class LoginControllerTest {
    private static final String BASE_URL = "http://localhost";
    private static final int PORT = 8080;

    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(BASE_URL + ":" + PORT);
    }

    @Test
    @DisplayName("로그인 화면 요청")
    void showLoginForm() {
        testClientBuilder.build()
                .get()
                .uri("/users/loginForm")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그인 요청 성공")
    void successLogin() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", "admin")
                        .with("password", "password"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("존재하지 않는 회원아이디로 로그인 요청")
    void failLogin() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", "done")
                        .with("password", "1234"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("일치하지 않는 비밀번호로 로그인 요청")
    void loginNotMatchPassword() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", "admin")
                        .with("password", "wrongPassword"))
                .exchange()
                .expectStatus().isOk();
    }
}
