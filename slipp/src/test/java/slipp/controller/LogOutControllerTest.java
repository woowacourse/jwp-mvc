package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

public class LogOutControllerTest {
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
    @DisplayName("로그아웃 요청")
    void logout() {
        testClientBuilder.build()
                .get()
                .uri("/users/logout")
                .exchange()
                .expectStatus().is3xxRedirection();
    }
}
