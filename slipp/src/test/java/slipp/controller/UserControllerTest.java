package slipp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

class UserControllerTest {
    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    ;

    @Test
    void 로그인_안하고_유저_목록_확인() {
        webTestClient.get().uri("/users").exchange().expectStatus().is3xxRedirection();
    }

}