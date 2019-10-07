package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

import java.net.URI;
import java.net.URISyntaxException;

class UserControllerTest {

    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

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
    @DisplayName("로그인 되어있을 때, 유저 목록 조회 성공")
    void user_list_not_loggedin() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users")).isFound();
    }

    @Test
    @DisplayName("비로그인 일 때, 유저 목록 조회 실패")
    void user_list_loggedin() throws URISyntaxException {
        String cookie = getCookie();
        NsWebTestClient.of(8080).getRequest(new URI("/users"), cookie).isOk();
    }

    private String getCookie() throws URISyntaxException {
        body = new LinkedMultiValueMap<>();
        body.add("userId", "pobi123");
        body.add("password", "passworD1!");
        String cookie = NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body).isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");

        log.debug("[cookie] : {}", cookie);
        return cookie;
    }
}