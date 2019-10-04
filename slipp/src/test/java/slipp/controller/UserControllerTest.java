package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import support.test.WebTestClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.of(8080);
    }

    @Test
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        Map<String, String> expected = new HashMap<>();
        expected.put("userId", "pobi");
        expected.put("password", "password");
        expected.put("name", "짜바지기");
        expected.put("email", "pobi@slipp.net");

        client.postResource("/users/create", expected, UserCreatedDto.class)
                .expectBody()
                .consumeWith(result -> {
                    String location = Objects.requireNonNull(result.getResponseHeaders().get("Location")).get(0);
                    assertThat(location).isEqualTo("/");
                });

       client.getResource("/users/profile?userId=pobi")
               .expectBody()
               .consumeWith(result -> {
                   String body = new String(Objects.requireNonNull(result.getResponseBody()), StandardCharsets.UTF_8);
                   assertThat(body).contains(expected.get("name"));
                   assertThat(body).contains(expected.get("email"));
               });
    }
}