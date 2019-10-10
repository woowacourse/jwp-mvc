package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void 유저_회원가입_정상() {
        client.build()
                .post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", "comdemcd")
                        .with("password", "pw1234").with("name", "코맥").with("email", "park@naver.com"))
                .exchange()
                .expectStatus()
                .isFound()
        ;
    }
}
