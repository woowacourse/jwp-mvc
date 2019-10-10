package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
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
    void userListWhileLoggedIn() {
        client.get()
                .uri("/users")
                .cookie("JSESSIONID", login(client, "admin", "password"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그아웃 상태에서 유저 목록 페이지 이동시 로그인 화면으로 리다이렉트")
    void userListWhileLoggedOut() {
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

    @Test
    @DisplayName("로그인 상태에서 유저 정보 수정 화면으로 이동")
    void userUpdateWhileLoggedIn() {
        client.get()
                .uri("/users/updateForm?userId=admin")
                .cookie("JSESSIONID", login(client, "admin", "password"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그아웃 상태에서 유저 정보 수정 화면으로 이동")
    void userUpdateWhileLoggedOut() {
        client.get()
                .uri("/users/updateForm?userId=admin")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("존재하는 유저 프로필 화면으로 이동")
    void userProfile() {
        client.get()
                .uri("/users/profile?userId=admin")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("존재하지 않는 유저 프로필 화면으로 이동")
    void notFoundUserProfile() {
        client.get()
                .uri("/users/profile?userId=notFound")
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("유저 생성후 redirect")
    void userCreate() {
        UserCreatedDto createUser =
                new UserCreatedDto("createTest", "password", "create", "create@nextstep.camp");

        client.post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", createUser.getUserId())
                        .with("password", createUser.getPassword())
                        .with("name", createUser.getName())
                        .with("email", createUser.getEmail()))
                .exchange()
                .expectStatus().isFound();
    }

    @Test
    @DisplayName("유저 정보 수정 후 redirect")
    void updateUser() {
        //생성
        UserCreatedDto createUser =
                new UserCreatedDto("updateTest", "password", "update", "update@nextstep.camp");
        client.post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", createUser.getUserId())
                        .with("password", createUser.getPassword())
                        .with("name", createUser.getName())
                        .with("email", createUser.getEmail()))
                .exchange()
                .expectStatus().isFound();

        //수정
        UserUpdatedDto updateUser = new UserUpdatedDto("password2", "코난", "conan@nextstep.camp");
        client.put()
                .uri("/users/update?userId=" + createUser.getUserId())
                .cookie("JSESSIONID", login(client, createUser.getUserId(), createUser.getPassword()))
                .body(BodyInserters.fromFormData("password", updateUser.getPassword())
                        .with("name", updateUser.getName())
                        .with("email", updateUser.getEmail()))
                .exchange()
                .expectStatus().isFound();
    }
}
