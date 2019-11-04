package slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HomeControllerTest extends BaseControllerTest {
    @Test
    @DisplayName("index페이지를 되돌려준다.")
    void index() {
        client.get("/")
                .exchange()
                .expectStatus().isOk();
    }
}