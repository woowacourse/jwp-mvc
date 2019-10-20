package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import support.test.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserControllerTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = WebTestClient.of(8080);
    }

    @Test
    void getLoginFormStatusOk() {
        assertDoesNotThrow(() -> client.getRequest("/users/form")
                .isFound()
                .expectHeader()
                .valueEquals("Location", "/"));
    }

    @Test
    void createUserStatusRedirect() {
        UserCreatedDto expected =
                new UserCreatedDto("pobi", "password", "포비", "pobi@nextstep.camp");
        assertDoesNotThrow(() -> client.postRequestWithBody("/users/create", expected, UserCreatedDto.class)
                .isFound()
                .expectHeader()
                .valueMatches("Location", "/"));

    }

    @Test
    void userListNotLoginRedirect() {
        assertDoesNotThrow(() -> client.getRequest("/users/list")
                .isFound()
                .expectHeader()
                .valueMatches("Location", "/users/loginForm"));
    }

}