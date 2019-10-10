package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import support.test.CustomWebTestClient;
import support.test.TestServerRunner;

public class UserControllerTest {
    private CustomWebTestClient client;

    @BeforeEach
    void setUp() {
        TestServerRunner.run();
        client = CustomWebTestClient.of(8080);
    }

    @Test
    void 회원가입_성공() {
        signUp("codemcd", "pw1234", "코맥", "park@naver.com");
    }

    @Test
    void 로그인_성공() {
        signUp("codemcd2", "pw1234", "코맥", "park@naver.com");

        client.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", "codemcd2")
                        .with("password", "pw1234"))
                .exchange()
                .expectStatus()
                .isFound()
        ;

    }

    private WebTestClient.ResponseSpec signUp(String userId, String password, String name, String email) {
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
