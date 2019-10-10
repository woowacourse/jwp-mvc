package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.test.NsWebTestClient;

class HomeControllerTest {

    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void indexPage() {
        nsWebTestClient.getRequest("/")
            .isOk();
    }

    @Test
    @DisplayName("로그인 화면으로 요청")
    void loginForm() {
        nsWebTestClient.getRequest("/users/loginForm")
            .isOk();
    }

    @Test
    @DisplayName("회원가입 화면으로 요청")
    void signUpForm() {
        nsWebTestClient.getRequest("/users/form")
            .isOk();
    }
}