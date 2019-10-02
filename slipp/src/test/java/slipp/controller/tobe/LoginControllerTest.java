package slipp.controller.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class LoginControllerTest {
    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }

    @Test
    void login() {
        MultiValueMap<String, String> loginInfo = new LinkedMultiValueMap<>();
        loginInfo.add("userId", "admin");
        loginInfo.add("password", "password");
        client.sendRequest(HttpMethod.POST, "/users/login", loginInfo).isFound();
    }
}