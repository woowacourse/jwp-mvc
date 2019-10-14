package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileControllerTest {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer()
                .baseUrl(baseUrl + ":" + 8080);
    }

    @Test
    void 로그인_후_프로필_조회() {
        ResponseCookie responseCookie = getResponseCookie();

        byte[] responseBody = testClientBuilder.build()
                .get().uri("/users/profile?userId=admin")
                .cookie(responseCookie.getName(), responseCookie.getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();

        String body = new String(responseBody);

        assertThat(body).contains("자바지기");
        assertThat(body).contains("admin@slipp.net");
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