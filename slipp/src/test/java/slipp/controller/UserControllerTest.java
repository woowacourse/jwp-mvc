package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

import static support.test.AcceptanceTestTemplate.getCookie;
import static support.test.AcceptanceTestTemplate.signUp;

class UserControllerTest {
    private MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

    @BeforeEach
    void setUp() {
        signUp();
    }

    @Test
    @DisplayName("로그인 일 때, 유저 목록 조회 성공")
    void user_list_loggedin() throws URISyntaxException {
        String cookie = getCookie();
        NsWebTestClient.of(8080).getRequest(new URI("/users"), cookie)
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("비로그인일 때, 유저 목록 조회 실패")
    void user_list_not_loggedin() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users"))
                .expectStatus().isFound();
    }

    @Test
    @DisplayName("유저 조회 성공")
    void find_user() throws URISyntaxException {
        body.clear();
        body.add("userId", "pobi123");
        NsWebTestClient.of(8080).postRequest(new URI("/users/profile"), body)
                .expectStatus().isOk();
    }
}