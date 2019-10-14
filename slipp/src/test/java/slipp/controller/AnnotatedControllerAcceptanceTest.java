package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.BodyInserters;
import support.test.NsWebServer;
import support.test.NsWebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedControllerAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private static final String USER_ID = "kjm";
    private static final String PASSWORD = "password";
    private static final String NAME = "김정민";
    private static final String EMAIL = "kjm@gmail.com";

    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        NsWebServer webServer = new NsWebServer();
        client = NsWebTestClient.of(8080);
    }

    @Test
    @DisplayName("홈페이지 Resources 회수 테스트")
    void homePage() {
        byte[] body = client.builder().build()
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk()
            .expectBody().returnResult().getResponseBody();

        assertThat(new String(body))
            .contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        client.builder().build()
            .post()
            .uri("/users/create")
            .body(BodyInserters.fromFormData("userId", USER_ID)
                .with("password", PASSWORD)
                .with("name", NAME)
                .with("email", EMAIL))
            .exchange()
            .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("로그인 테스트")
    void logIn() {
        signUp(USER_ID, PASSWORD, NAME, EMAIL);

        client.builder().build()
            .post()
            .uri("/users/login")
            .body(BodyInserters.fromFormData("userId", USER_ID)
                .with("password", PASSWORD))
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueEquals("Location", "/");
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void logInFail() {
        byte[] body = client.builder().build()
            .post()
            .uri("/users/login")
            .body(BodyInserters.fromFormData("userId", USER_ID)
                .with("password", "invalid"))
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .returnResult()
            .getResponseBody();

        assertThat(new String(body)).contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
    }

    @Test
    @DisplayName("로그인 성공후 유저리스트 가져오기 테스트")
    void getUserList() {
        signUp(USER_ID, PASSWORD, NAME, EMAIL);
        String cookie = login("kjm", "password");

        byte[] body = client.builder().build()
            .get()
            .uri("/users")
            .header("Cookie", cookie)
            .exchange()
            .expectStatus().isOk()
            .expectBody().returnResult().getResponseBody();

        assertThat(new String(body)).contains("kjm@gmail.com");
    }

    @Test
    @DisplayName("로그인 실패 후 유저리스트 가져오기 테스트 실패")
    void getUserListFail() {
        client.builder().build()
            .get()
            .uri("/users")
            .exchange()
            .expectStatus().is3xxRedirection()
            .expectHeader().valueEquals("Location", "/users/loginForm");
    }

    private void signUp(String userId, String password, String name, String email) {
        client.builder().build()
            .post()
            .uri("/users/create")
            .body(BodyInserters.fromFormData("userId", userId)
                .with("password", password)
                .with("name", name)
                .with("email", email))
            .exchange()
            .expectStatus().is3xxRedirection();
    }

    private String login(String userId, String password) {
        return client.builder().build()
            .post()
            .uri("/users/login")
            .body(BodyInserters.fromFormData("userId", userId)
                    .with("password", password))
            .exchange()
            .expectStatus().is3xxRedirection()
            .returnResult(String.class)
            .getResponseHeaders()
            .getFirst("Set-Cookie");
    }
}
