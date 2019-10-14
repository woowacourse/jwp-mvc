package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.test.NsWebTestClient;

class HomeControllerTest {
    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }


    @Test
    void 홈_화면_이동() {
        client.getResourceWithoutBody("/");
    }
}