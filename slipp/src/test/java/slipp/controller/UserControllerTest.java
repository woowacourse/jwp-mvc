package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import slipp.domain.User;
import slipp.support.db.DataBase;

import java.util.stream.Stream;

public class UserControllerTest {
    private static final String BASE_URL = "http://localhost";
    private static final int PORT = 8080;

    private WebTestClient.Builder testClientBuilder;

    @BeforeEach
    void setUp() {
        this.testClientBuilder = WebTestClient
                .bindToServer()
                .baseUrl(BASE_URL + ":" + PORT);
    }

    @Test
    @DisplayName("로그인 아닌 상태로 회원 리스트 요청")
    void showUserListWithoutLogin() {
        testClientBuilder.build()
                .get()
                .uri("/users")
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("로그인 상태로 회원 리스트 요청")
    void showUserList() {
        String jSessionId = getJSessionId("admin", "password");
        testClientBuilder.build()
                .get()
                .uri("/users")
                .cookie("JSESSIONID", jSessionId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("회원가입 화면 요청")
    void showSignUpForm() {
        testClientBuilder.build()
                .get()
                .uri("/users/form")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("회원 프로필 화면 요청")
    void showProfile() {
        testClientBuilder.build()
                .get()
                .uri("/users/profile?userId=admin")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("존재하지 않는 회원 프로필 화면 요청")
    void failToShowProfile() {
        testClientBuilder.build()
                .get()
                .uri("/users/profile?userId=empty")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("로그인한 회원의 회원정보 수정화면 요청")
    void modifyForm() {
        String jSessionId = getJSessionId("admin", "password");
        testClientBuilder.build()
                .get()
                .uri("/users/updateForm?userId=admin")
                .cookie("JSESSIONID", jSessionId)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    @DisplayName("로그인한 회원이 아닌 다른 회원의 정보 수정화면 요청")
    void modifyFormToAnotherUser() {
        DataBase.addUser(new User("done", "password", "done", "done@gmail.com"));
        String jSessionId = getJSessionId("admin", "password");
        testClientBuilder.build()
                .get()
                .uri("/users/updateForm?userId=done")
                .cookie("JSESSIONID", jSessionId)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("회원가입 요청")
    void createUser() {
        testClientBuilder.build()
                .post()
                .uri("/users/create")
                .body(BodyInserters
                        .fromFormData("userId", "done")
                        .with("password", "password")
                        .with("name", "done")
                        .with("email", "done@gmail.com"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("회원정보 수정")
    void modifyUserInfo() {
        String jSessionId = getJSessionId("admin", "password");
        testClientBuilder.build()
                .post()
                .uri("/users/update")
                .cookie("JSESSIONID", jSessionId)
                .body(BodyInserters
                        .fromFormData("userId", "admin")
                        .with("password", "password")
                        .with("name", "done")
                        .with("email", "done@gmail.com"))
                .exchange()
                .expectStatus().is3xxRedirection();
    }

    @Test
    @DisplayName("다른 회원의 회원정보 수정")
    void modifyAnotherUserInfo() {
        String jSessionId = getJSessionId("admin", "password");
        testClientBuilder.build()
                .post()
                .uri("/users/update")
                .cookie("JSESSIONID", jSessionId)
                .body(BodyInserters
                        .fromFormData("userId", "done")
                        .with("password", "password")
                        .with("name", "done")
                        .with("email", "done@gmail.com"))
                .exchange()
                .expectStatus().is5xxServerError();
    }

    private String getJSessionId(String userId, String password) {
        EntityExchangeResult<byte[]> loginResult = testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", userId)
                        .with("password", password))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", ".*/.*")
                .expectBody()
                .returnResult();

        return extractJSessionId(loginResult);
    }

    private String extractJSessionId(EntityExchangeResult<byte[]> loginResult) {
        String[] cookies = loginResult.getResponseHeaders().get("Set-Cookie").stream()
                .filter(it -> it.contains("JSESSIONID"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("JSESSIONID가 없습니다."))
                .split(";");
        return Stream.of(cookies)
                .filter(it -> it.contains("JSESSIONID"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("JSESSIONID가 없습니다."))
                .split("=")[1];
    }
}
