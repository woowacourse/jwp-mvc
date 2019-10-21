package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class CreateUserControllerTest {
    private static final String DEFAULT_URL = "http://localhost";
    private static final int DEFAULT_PORT = 8080;

    WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer().baseUrl(DEFAULT_URL + ":" + DEFAULT_PORT);
    }

    @Test
    @DisplayName("회원가입 페이지로 이동한다.")
    void userForm() {
        testClientBuilder.build().get()
                .uri("/users/form")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void createUser() {
        testClientBuilder.build()
                .post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", "hyo")
                        .with("password", "123")
                        .with("name", "hyojae")
                        .with("email", "hyo@test.com"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}