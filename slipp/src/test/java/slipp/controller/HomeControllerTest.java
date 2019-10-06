package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.test.NsWebTestClient;

class HomeControllerTest {

    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void indexPage() {
        nsWebTestClient.get("/").isOk();
    }
}