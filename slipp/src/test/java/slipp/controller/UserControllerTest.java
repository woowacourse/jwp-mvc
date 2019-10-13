package slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest extends TestTemplate {

    @Test
    @DisplayName("유저 생성 테스트")
    void create() {
        requestWithFormData(HttpMethod.POST, "/users/create",
                BodyInserters.fromFormData("userId", "pobi")
                        .with("password", "password")
                        .with("name", "포비")
                        .with("email", "pobi@nextstep.camp"), HttpStatus.FOUND);
    }

    @Test
    @DisplayName("로그인 안된 상태에서 유저 목록 요청")
    void before_login_showUserList() {
        request(HttpMethod.GET, "/users", HttpStatus.FOUND);
    }

    @Test
    @DisplayName("로그인 된 상태에서 유저 목록 요청")
    void after_login_showUserList() {
        loginAndRequest(HttpMethod.GET, "/users", HttpStatus.OK)
                .expectBody()
                .consumeWith((res) -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains(USER_ID));
                });
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() {
        requestWithFormData(HttpMethod.POST, "/users/login", BodyInserters.fromFormData("userId", USER_ID)
                .with("password", USER_PASSWORD), HttpStatus.FOUND);
    }

    @Test
    @DisplayName("로그아웃 테스트")
    void logout() {
        loginAndRequest(HttpMethod.GET, "/users/logout", HttpStatus.FOUND);
    }

    @Test
    @DisplayName("유저 정보 보기 테스트")
    void showProfile() {
        loginAndRequest(HttpMethod.GET, "/users/profile?userId=" + USER_ID, HttpStatus.OK)
                .expectBody()
                .consumeWith((res) -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains(USER_ID));
                });
    }

    @Test
    @DisplayName("로그인 상태에서 수정 폼 보기")
    void showUpdateForm() {
        loginAndRequest(HttpMethod.GET, "/users/updateForm?userId=" + USER_ID, HttpStatus.OK)
                .expectBody()
                .consumeWith((res) -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains(USER_ID));
                });
    }

    @Test
    @DisplayName("다른유저 유저 수정 테스트")
    void update_noAuth() {
        loginAndRequestWithFormData(HttpMethod.POST, "/users/update",
                BodyInserters.fromFormData("userId", "pobi")
                        .with("password", "password")
                        .with("name", "포비")
                        .with("email", "pobi@nextstep.camp"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("본인 정보 수정 후 조회 테스트")
    void update_correct() {
        // 수정 요청
        loginAndRequestWithFormData(HttpMethod.POST, "/users/update",
                BodyInserters.fromFormData("userId", "admin")
                        .with("password", "password")
                        .with("name", "포비")
                        .with("email", "pobi@nextstep.camp"), HttpStatus.FOUND);

        // 조회
        loginAndRequest(HttpMethod.GET, "/users", HttpStatus.OK)
                .expectBody()
                .consumeWith((res) -> {
                    String body = new String(res.getResponseBody());
                    assertTrue(body.contains("admin"));
                    assertTrue(body.contains("포비"));
                    assertTrue(body.contains("pobi@nextstep.camp"));
                });
    }

    @Test
    @DisplayName("유저 폼 보기")
    void showUserForm() {
        request(HttpMethod.GET, "/users/form", HttpStatus.OK);
    }

    @Test
    @DisplayName("로그인 폼 보기")
    void showLoginForm() {
        request(HttpMethod.GET, "/users/loginForm", HttpStatus.OK);
    }
}