package support.test;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

public class TestUtils {
    public static String login(WebTestClient webTestClient, String userId, String password) {
        return webTestClient.post().uri("/users/login")
                .body(BodyInserters.fromFormData("userId", userId)
                        .with("password", password))
                .exchange()
                .returnResult(String.class)
                .getResponseCookies().get("JSESSIONID").get(0).getValue();
    }
}
