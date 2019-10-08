package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import support.test.NsWebTestClient;

class HomeControllerTest {

    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }

    @Test
    void home() {
        client.sendRequest(HttpMethod.GET, "/").isOk();
    }
}