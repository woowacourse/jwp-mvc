package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.dto.UserUpdatedDto;
import support.test.WebTestClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.of(8080);
        webTestClient.login();
    }

    @Test
    @DisplayName("유저 업데이트 페이지 노출")
    void showUpdateForm() {
        webTestClient.loginGetResource("/users/updateForm?userId=admin")
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("유저 업데이트")
    void update() {
        Map<String, String> expected = new HashMap<>();
        expected.put("password", "password");
        expected.put("name", "짜바지기");
        expected.put("email", "pobi@slipp.net");

        webTestClient.loginPostResource("/users/update?userId=admin", expected, UserUpdatedDto.class)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });
    }

    @Test
    @DisplayName("유저 프로필 확인")
    void showProfile() {
        webTestClient.getResource("/users/profile?userId=admin")
                .expectBody()
                .consumeWith(result -> {
                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertThat(body).contains("Profiles");
                });
    }

    @Test
    @DisplayName("유저 리스트 확인")
    void showUsers() {
        webTestClient.loginGetResource("/users")
                .expectBody()
                .consumeWith(result -> {
                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertThat(body).contains("사용자 아이디", "이름", "이메일");
                });
    }
}