package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import support.test.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class HomeControllerTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.of(8080);
    }

    @Test
    void getHomeStatusIsOk(){
        assertDoesNotThrow(() -> client.getRequest("/").isOk());
    }
}