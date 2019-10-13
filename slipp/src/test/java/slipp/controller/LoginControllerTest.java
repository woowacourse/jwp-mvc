package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

import static support.test.AcceptanceTestTemplate.login;
import static support.test.AcceptanceTestTemplate.signUp;

class LoginControllerTest {
    private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    @BeforeEach
    void setUp() {
        signUp();
    }

    @Test
    @DisplayName("로그인 성공")
    void login_success() {
        login();
    }

    @Test
    @DisplayName("로그인 실패")
    void login_fail() throws URISyntaxException {
        body.clear();
        body.add("userId", "pobi123");
        body.add("password", "incorrectPW");

        NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body)
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logout() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/logout"))
                .expectStatus().isFound();
    }
}