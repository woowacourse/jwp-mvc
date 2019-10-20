package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.test.WebTestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class UserAuthControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.of(8080);
    }

    @Test
    @DisplayName("유저 생성 페이지 노출")
    void showCreateForm() {
        webTestClient.getResource("/users/form")
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("유저 생성 성공")
    void create() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "pobi");
        expected.put("password", "password");
        expected.put("name", "짜바지기");
        expected.put("email", "pobi@slipp.net");

        webTestClient.postResource("/users/create", expected)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });
    }

    @Test
    @DisplayName("로그인 페이지 노출")
    void showLoginForm() {
        webTestClient.getResource("/users/loginForm")
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("로그인 성공")
    void login() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "admin");
        expected.put("password", "password");
        webTestClient.postResource("/users/login", expected)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });
    }

    @Test
    @DisplayName("로그아웃")
    void logout() {
        webTestClient.getResource("/users/logout")
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });
    }
}