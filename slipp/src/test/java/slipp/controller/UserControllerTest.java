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
class UserControllerTest {
    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    private String cookie;

    @BeforeAll
    void setUp_회원가입() {
        MultiValueMap<String, String> queryString = new LinkedMultiValueMap<>();
        queryString.add("userId", "id");
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
                .body(fromFormData("userId", "id")
                        .with("password", "password"))
                .exchange()
                .returnResult(String.class).getResponseHeaders().getFirst("Set-Cookie");
    }

    @Test
    void 로그인_안하고_유저_목록_확인() {
        webTestClient.get().uri("/users").exchange().expectStatus().is3xxRedirection();
    }

    @Test
    void 로그인_하고_유저_목록_확인() {
        webTestClient.get()
                .uri("/users")
                .header("cookie", cookie)
                .exchange()
                .expectStatus()
                .isOk();
    }
}