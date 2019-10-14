package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class LogoutControllerTest {

    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }

    @Test
    void logout_with_logined() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "conas");
        multiValueMap.add("password", "12345");

        client.getRequestWithLogined("/users/logout", multiValueMap)
            .isFound();
    }

    @Test
    void logout_without_logined() {
        client.getRequest("/users/logout")
            .isUnauthorized();
    }
}