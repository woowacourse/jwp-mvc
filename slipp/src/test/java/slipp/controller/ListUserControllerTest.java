package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.Assertions.assertThat;

class ListUserControllerTest {
    private static final String BASE_URL = "http://localhost";

    private String baseUrl = BASE_URL;
    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        testClientBuilder = WebTestClient.bindToServer()
                .baseUrl(baseUrl + ":" + 8080);
    }

    @Test
    void 로그인_없이_유저_목록_페이지_이동() {
        testClientBuilder.build()
                .get()
                .uri("/users")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueMatches("location", ".*/users/loginForm");
    }

    @Test
    void 로그인_하고_유저_목록_페이지_이동() {
        ResponseCookie responseCookie = getResponseCookie();

        byte[] responseBody = testClientBuilder.build()
                .get()
                .uri("/users")
                .cookie(responseCookie.getName(), responseCookie.getValue())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult().getResponseBody();

        assertThat(new String(responseBody).contains("admin")).isTrue();

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