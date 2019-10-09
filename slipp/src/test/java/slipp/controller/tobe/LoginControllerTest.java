package slipp.controller.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import support.test.NsWebTestClient;

public class LoginControllerTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080).getWebTestClient();
    }

    @Test
    @DisplayName("유저가 없을시 로그인 페이지로 이동")
    void loginNotFoundUser() {
        client.post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "NotFoundUser")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("login 성공시 home으로 redirect")
    void loginSuccess() {
        client.post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isFound();
    }

    @Test
    @DisplayName("login 실패시 로그인 페이지로 이동")
    void loginFail() {
        client.post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "invalid"))
                .exchange()
                .expectStatus().isOk();
    }
}
