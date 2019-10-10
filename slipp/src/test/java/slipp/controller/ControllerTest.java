package slipp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

public class ControllerTest {
    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    @Test
    void 홈페이지() {
        webTestClient.get().uri("/").exchange().expectStatus().isOk();
    }

    @Test
    void 회원가입페이지() {
        webTestClient.get().uri("/users/form").exchange().expectStatus().isOk();
    }

    @Test
    void 로그인페이지() {
        webTestClient.get().uri("/users/loginForm").exchange().expectStatus().isOk();
    }

    //todo 로그아웃, 프로필사진, 업데이트 페이지 테스트
}
