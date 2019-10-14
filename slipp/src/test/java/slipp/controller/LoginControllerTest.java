package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("유효한 유저 로그인")
    void login() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "conas");
        multiValueMap.add("password", "12345");

        client.postRequest("/users/login", multiValueMap)
            .isFound();
    }

    @Test
    @DisplayName("유효하지 않은 로그인")
    void login_with_invalid_user() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "goooooooooooooood");
        multiValueMap.add("password", "thisIsPassword");

        client.postRequest("/users/login", multiValueMap)
            .isOk();
    }
}