package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

public class HomeControllerTest {
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
    @DisplayName("메인화면 요청")
    void showMain() {
        testClientBuilder.build()
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk();
    }
}
