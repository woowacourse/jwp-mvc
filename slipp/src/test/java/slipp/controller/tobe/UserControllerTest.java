package slipp.controller.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import support.test.NsWebTestClient;

class UserControllerTest {
    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }

    @Test
    void userList() {
        client.sendRequest(HttpMethod.GET, "/users").isFound();
    }

    @Test
    void userForm() {
        client.sendRequest(HttpMethod.GET, "/users/form").isOk();
    }
}