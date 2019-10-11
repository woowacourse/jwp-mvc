package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.test.WebTestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class TestUserControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.of(8080);
    }

    @Test
    void create_string() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "admin");
        expected.put("password", "password");

        webTestClient.postResource("/test/users/1", expected)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });;
    }

    @Test
    void create_int_long() {
        Map<String, String> expected = new HashMap<>();
        expected.put("id", "11111");
        expected.put("age", "111");

        webTestClient.postResource("/test/users/2", expected)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });;
    }

    @Test
    void create_javabean() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "admin");
        expected.put("password", "password");

        webTestClient.postResource("/test/users/3", expected)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });;
    }
}