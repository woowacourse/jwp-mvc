package slipp.controller;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import support.test.CustomWebTestClient;

public class UserControllerTemplate {
    protected static final String USER_ID1 = "comac";
    protected static final String USER_ID2 = "comac2";
    protected static final String PASSWORD = "pw1234";
    protected static final String NAME = "코맥";
    protected static final String EMAIL = "park@naver.com";

    protected CustomWebTestClient client;

    protected String getCookie(String userId, String password) {
        return login(userId, password)
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie")
                ;
    }

    protected WebTestClient.ResponseSpec login(String userId, String password) {
        return client.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", userId)
                        .with("password", password))
                .exchange()
                .expectStatus()
                .isFound()
                ;
    }

    protected WebTestClient.ResponseSpec signUp(String userId, String password, String name, String email) {
        return client.build()
                .post()
                .uri("/users/create")
                .body(BodyInserters
                        .fromFormData("userId", userId)
                        .with("password", password)
                        .with("name", name)
                        .with("email", email))
                .exchange()
                .expectStatus()
                .isFound()
                ;
    }
}
