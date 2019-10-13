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
    private static final String USER_ID = "pobi123";

    @BeforeEach
    void setUp() throws URISyntaxException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("userId", USER_ID);
        body.add("password", "passworD1!");
        body.add("name", "pobi");
        body.add("email", "pobi123@gmail.com");

        NsWebTestClient.of(8080).postRequest(new URI("/users/create"), body).isFound();
    }

    @Test
    @DisplayName("회원가입 폼 이동")
    void signUpForm() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/form")).isOk();
    }

    @Test
    @DisplayName("비로그인 일 때, 유저 목록 조회 실패")
    void user_list_not_loggedin() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users")).isFound();
    }

    @Test
    @DisplayName("로그인 되어있을 때, 유저 목록 조회 성공")
    void user_list_loggedin() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users"), getCookie()).isOk();
    }

    @Test
    @DisplayName("프로필 조회 성공")
    void show_user_success() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/profile?userId=" + USER_ID)).isOk();
    }

    @Test
    @DisplayName("프로필 조회 실패")
    void show_user_fail() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/profile")).isBadRequest();
    }

    @Test
    @DisplayName("업데이트 폼 조회 성공")
    void show_update_form_success() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/profile?userId=" + USER_ID), getCookie()).isOk();
    }

    @Test
    @DisplayName("업데이트 폼 조회 실패")
    void show_update_form_fail() throws URISyntaxException {
        NsWebTestClient.of(8080).getRequest(new URI("/users/profile?userId" + USER_ID + "fail"), getCookie()).isBadRequest();
    }

    @Test
    @DisplayName("유저 업데이트 성공")
    void update_uesr_success() throws URISyntaxException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("userId", USER_ID);
        body.add("password", "updatedPassword");
        body.add("name", "updatedName");
        body.add("email", "updatedEmail");

        NsWebTestClient.of(8080).postRequest(new URI("/users/update"), body, getCookie()).isFound();
    }

    @Test
    void update_uesr_fail() throws URISyntaxException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("userId", USER_ID + "fail");
        body.add("password", "updatedPassword");
        body.add("name", "updatedName");
        body.add("email", "updatedEmail");

        NsWebTestClient.of(8080).postRequest(new URI("/users/update"), body, getCookie()).isBadRequest();
    }

    private String getCookie() throws URISyntaxException {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("userId", USER_ID);
        body.add("password", "passworD1!");
        String cookie = NsWebTestClient.of(8080).postRequest(new URI("/users/login"), body).isFound()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");

        log.debug("[cookie] : {}", cookie);
        return cookie;
    }
}