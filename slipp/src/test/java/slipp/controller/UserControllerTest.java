package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import support.test.TestHelper;
import support.test.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserControllerTest {
    UserCreatedDto testUser;
    private WebTestClient client;
    private TestHelper helper;

    @BeforeEach
    void setUp() {
        client = WebTestClient.of(8080);
        helper = new TestHelper(client);

        testUser = new UserCreatedDto("pobi", "password", "포비", "pobi@pobi.com");
        helper.createUser(testUser);
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
        UserCreatedDto userCreatedDto = new UserCreatedDto("pobi", "password", "포비", "pobi@pobi.com");
        assertDoesNotThrow(() -> helper.createUser(userCreatedDto)
                .isFound()
                .expectHeader()
                .valueMatches("Location", "/"));
    }

    @Test
    void createUserDataCheck() {
        String bite = client.getRequestWithCookie("/users/list", helper.getCookie())
                .isOk()
                .expectBody()
                .returnResult()
                .toString();
        assertThat(bite).contains(testUser.getEmail());
    }

    @Test
    void userListNotLoginRedirect() {
        assertDoesNotThrow(() -> client.getRequest("/users/list")
                .isFound()
                .expectHeader()
                .valueMatches("Location", "/users/loginForm"));
    }
}