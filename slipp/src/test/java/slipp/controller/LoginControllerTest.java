package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class LoginControllerTest {
    private static final String DEFAULT_URL = "http://localhost";
    private static final int DEFAULT_PORT = 8080;

    WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer().baseUrl(DEFAULT_URL + ":" + DEFAULT_PORT);
    }

    @Test
    @DisplayName("로그인 페이지로 이동한다.")
    void loginPage() {
        testClientBuilder.build()
                .get()
                .uri("/users/loginForm")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그인에 성공한다.")
    void login() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 로그인을 하면 실패한다.")
    void 존재하지_않는_아이디로_로그인_실패() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin2")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 로그인 실패한다.")
    void 비밀번호_일치하지_않을때_로그인_실패() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password2"))
                .exchange()
                .expectStatus().isOk();
    }
}