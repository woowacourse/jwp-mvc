package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

class LogoutControllerTest {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer()
                .baseUrl(baseUrl + ":" + 8080);
    }

    @Test
    void 로그아웃_성공() {
        ResponseCookie responseCookie = getResponseCookie();

        testClientBuilder.build()
                .get().uri("/users/logout")
                .cookie(responseCookie.getName(), responseCookie.getValue())
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("location", ".*/");
    }

    private ResponseCookie getResponseCookie() {
        return testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", "admin").with("password", "password"))
                .exchange()
                .expectBody()
                .returnResult().getResponseCookies().get("JSESSIONID").get(0);
    }
}