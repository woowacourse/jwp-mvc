package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import support.test.CustomWebTestClient;
import support.test.TestServerRunner;

public class UserControllerTest {
    private static final String USER_ID1 = "comac";
    private static final String USER_ID2 = "comac2";
    private static final String PASSWORD = "pw1234";
    private static final String NAME = "코맥";
    private static final String EMAIL = "park@naver.com";

    private CustomWebTestClient client;

    @BeforeEach
    void setUp() {
        TestServerRunner.run();
        client = CustomWebTestClient.of(8080);
    }

    @Test
    void 회원가입_성공() {
        signUp(USER_ID1, PASSWORD, NAME, EMAIL);
    }

    @Test
    void 로그인_성공() {
        signUp(USER_ID2, PASSWORD, NAME, EMAIL);

        client.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters
                        .fromFormData("userId", USER_ID2)
                        .with("password", PASSWORD))
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
