package slipp.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogoutControllerTest {
    public static final String id = "id";
    public static final String USER_ID = "userId";
    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    private String cookie;

    @BeforeAll
    void setUp_회원가입_및_로그인() {
        MultiValueMap<String, String> queryString = new LinkedMultiValueMap<>();
        queryString.add(USER_ID, id);
        queryString.add("password", "password");
        queryString.add("name", "name");
        queryString.add("email", "email");

        webTestClient.post().uri("/users/create")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData(queryString))
                .exchange()
                .expectStatus()
                .isFound();

        cookie = webTestClient.post().uri("/users/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData(USER_ID, "id")
                        .with("password", "password"))
                .exchange()
                .returnResult(String.class).getResponseHeaders().getFirst("Set-Cookie");

        webTestClient.get().uri("/users/updateForm?" + USER_ID + "=" + id)
                .header("cookie", cookie)
                .exchange().expectStatus().isOk();

    }

    @Test
    void 로그아웃() {
        webTestClient.get().uri("/users/logout")
                .header("cookie", cookie)
                .exchange()
                .expectStatus()
                .isFound();

        webTestClient.get().uri("/users/updateForm?" + USER_ID + "=" + id)
                .header("cookie", cookie)
                .exchange().expectStatus().is5xxServerError();
    }
}
