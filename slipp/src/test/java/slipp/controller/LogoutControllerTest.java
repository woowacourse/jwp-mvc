package slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

class LogoutControllerTest extends BaseControllerTest {
    @Test
    @DisplayName("사용자 로그아웃")
    void logout() throws URISyntaxException {
        client.get("/users/logout")
                .exchange()
                .expectHeader()
                .valueEquals("location", "/");
    }
}