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

import java.net.URI;

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
        // 회원가입
        UserCreatedDto expected = new UserCreatedDto("pobi", "password", "포비", "pobi@nextstep.camp");

        client.createResource("/users/create", expected, UserCreatedDto.class)
                .expectBody()
                .consumeWith(result -> {
                    String location = result.getResponseHeaders().get("Location").get(0);
                    assertThat(location).isEqualTo("/");
                });
    }

    private void updateUser(String location) {
        // 수정
        UserUpdatedDto updateUser = new UserUpdatedDto("password2", "코난", "conan@nextstep.camp");
        client.updateResource(location, updateUser, UserUpdatedDto.class);

        User actual = client.getResource(location, User.class);
        assertThat(actual.getPassword()).isEqualTo(updateUser.getPassword());
        assertThat(actual.getName()).isEqualTo(updateUser.getName());
        assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    }

    private User getUser(UserCreatedDto expected, String location) {
        //조회
        User actual = client.getResource(location, User.class);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        return actual;
    }
}