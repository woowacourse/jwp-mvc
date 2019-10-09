package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private static final String BASE_URL = "http://localhost";
    private static final int BASE_PORT = 8080;

    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    public void setUp() {
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(BASE_URL + ":" + BASE_PORT);
        this.testClientBuilder.responseTimeout(Duration.ofSeconds(15000));
    }

    @Test
    void create() {
        testClientBuilder.build()
                .post()
                .uri("/users/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "pobi")
                        .with("password", "password")
                        .with("name", "포비")
                        .with("email", "pobi@nextstep.camp"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", "/");
    }

    @Test
    void update() {
        ResponseCookie cookies = loginThenGetCookies();

        logger.debug("cookies : {}", cookies);

        testClientBuilder.build()
                .post()
                .uri("/users/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .cookie(cookies.getName(), cookies.getValue())
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password")
                        .with("name", "자바지기")
                        .with("email", "pobi2@nextstep.camp"))
                .exchange()
                .expectStatus().isFound()
                .expectHeader().valueMatches("Location", "/");
    }

    @Test
    void profile() {
        ResponseCookie cookie = loginThenGetCookies();

        String responseBody = new String(
                Objects.requireNonNull(
                        testClientBuilder.build()
                                .get()
                                .uri("/users/profile?userId=admin")
                                .cookie(cookie.getName(), cookie.getValue())
                                .exchange()
                                .expectStatus().isOk()
                                .expectBody()
                                .returnResult()
                                .getResponseBody()));

        assertThat(responseBody.contains("pobi")).isTrue();
    }

    private ResponseCookie loginThenGetCookies() {
        return testClientBuilder.build()
                .post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password"))
                .exchange()
                .expectStatus().isFound()
                .expectBody()
                .returnResult()
                .getResponseCookies().get("JSESSIONID").get(0);
    }
}
