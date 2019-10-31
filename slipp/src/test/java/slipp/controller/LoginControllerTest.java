package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

class LoginControllerTest {

    private MultiValueMap<String, String> body;

    @BeforeEach
    void setUp() throws URISyntaxException {
        body = new LinkedMultiValueMap<>();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");
        body.add("name", "pobi");
        body.add("email", "pobi123@gmail.com");

        NsWebTestClient.of(8080).postRequest(new URI("/users/create"), body).isFound();
    }

    @Test
    @DisplayName("로그인 폼 이동")
    void loginForm() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/loginForm")).isOk();
    }

    @Test
    @DisplayName("로그인")
    void login() throws URISyntaxException {
        body = new LinkedMultiValueMap<>();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");

        NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body).isFound();
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws URISyntaxException {
        body = new LinkedMultiValueMap<>();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");

        NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body).isFound();

        NsWebTestClient.of(8080).getRequest(new URI("users/logout")).isFound();
    }
}