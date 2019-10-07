package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

class LoginUserControllerTest {

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
    void login() throws URISyntaxException {
        body = new LinkedMultiValueMap<>();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");

        NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body).isFound();
    }
}