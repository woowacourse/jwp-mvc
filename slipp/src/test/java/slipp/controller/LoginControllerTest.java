package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

class LoginControllerTest {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer()
                .baseUrl(baseUrl + ":" + 8080);
    }

    @Test
    void 로그인_페이지_이동() {
        testClientBuilder.build()
                .get()
                .uri("/users/loginForm")
                .exchange()
                .expectStatus()
                .isOk();
    }


    @Test
    void 로그인_성공() {
        testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin").with("password", "password"))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueMatches("location", ".*/");
    }

    @Test
    void 로그인_실패() {
        byte[] responseBody = testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "exception").with("password", "exception"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();

        assertThat(new String(responseBody)).contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
    }
}