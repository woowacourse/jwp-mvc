package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.test.NsWebTestClient;

public class HomeControllerTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080).getWebTestClient();
    }

    @Test
    @DisplayName("home으로 이동")
    void home() {
        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk();
    }
}
