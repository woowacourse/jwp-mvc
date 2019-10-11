package slipp.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import slipp.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
    public static final String id = "id";
    public static final String USER_ID = "userId";
    public static final String UPDATED_PASSWORD = "updated";
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

    @Test
    void 유저_정보_수정() {
        MultiValueMap<String, String> updatedQueryString = new LinkedMultiValueMap<>();
        updatedQueryString.add(USER_ID, "id");
        updatedQueryString.add("password", UPDATED_PASSWORD);
        updatedQueryString.add("name", "name");
        updatedQueryString.add("email", "email");

        webTestClient.post().uri("/users/update")
                .header("cookie", cookie)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(fromFormData(updatedQueryString))
                .exchange()
                .expectStatus()
                .isFound();

        StringBuilder location = new StringBuilder("/api/users?");
        location.append(USER_ID).append("=").append(id);

        User updatedUser = webTestClient.get()
                .uri(location.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .returnResult().getResponseBody();

        assertNotNull(updatedUser);
        assertThat(updatedUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }
}