package slipp.controller.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import support.test.NsWebTestClient;

import static support.test.TestUtils.login;

public class UserControllerTest {
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080).getWebTestClient();
    }

    @Test
    @DisplayName("로그인 상태에서 유저 목록 페이지로 이동시 정상 이동")
    void userListWithLogin() {
        client.get()
                .uri("/users")
                .cookie("JSESSIONID", login(client, "admin", "password"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그아웃 상태에서 유저 목록 페이지 이동시 로그인 화면으로 리다이렉트")
    void userListWithLogout() {
        client.get()
                .uri("/users")
                .exchange()
                .expectStatus().isFound();
    }

    @Test
    @DisplayName("회원가입 화면으로 이동")
    void userForm() {
        client.get()
                .uri("/users/form")
                .exchange()
                .expectStatus().isOk();
    }
}
