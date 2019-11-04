package slipp.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserLoginDto;
import slipp.dto.UserUpdatedDto;

import static org.springframework.web.reactive.function.BodyInserters.fromFormData;

class UserControllerTest extends BaseControllerTest {
    @Test
    @DisplayName("회원가입 페이지를 되돌려준다.")
    void creationForm() {
        client.get("/users/form")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("로그인 페이지를 되돌려준다.")
    void loginForm() {
        client.get("/users/loginForm")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저 정보 수정 페이지를 되돌려준다.")
    void updateForm() {
        // Given
        UserLoginDto loginDto = new UserLoginDto("loginFormTest", "password");
        createUserForLogin(loginDto.getUserId(), loginDto.getPassword());

        // When, Then
        String cookie = client.getLoginCookie(loginDto);

        client.get("/users/updateForm?userId=" + loginDto.getUserId())
                .header("cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저를 생성한다.")
    void createUser() {
        UserCreatedDto userCreatedDto = new UserCreatedDto(
                "userId",
                "password",
                "name",
                "email@email.com"
        );

        client.post("/users/create")
                .body(fromFormData("userId", userCreatedDto.getUserId())
                        .with("password", userCreatedDto.getPassword())
                        .with("name", userCreatedDto.getName())
                        .with("email", userCreatedDto.getEmail()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/");
    }

    @Test
    @DisplayName("로그인하지 않고 유저 목록에 접근하는 경우 로그인 페이지로 리다이렉트한다.")
    void userList_ifClient_isNotLogined() {
        client.get("/users/list")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/users/loginForm");
    }

    @Test
    @DisplayName("로그인 하고 유저 목록에 접근하면 로그인 페이지를 보여준다.")
    void userList_ifClient_isLogined() {
        // Given
        UserLoginDto loginDto = new UserLoginDto("loginTest", "password");
        createUserForLogin(loginDto.getUserId(), loginDto.getPassword());

        // When, Then
        String cookie = client.getLoginCookie(loginDto);

        client.get("/users/list")
                .header("cookie", cookie)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저 프로필을 되돌려준다.")
    void userProfile() {
        // TODO: 2019-11-05 userId 변경
        client.get("/users/profile?userId=admin")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("유저 정보를 수정한다.")
    void updateUser() {
        // Given
        UserLoginDto loginDto = new UserLoginDto("updateTest", "password");
        createUserForLogin(loginDto.getUserId(), loginDto.getPassword());
        UserUpdatedDto updatedUser = new UserUpdatedDto(
                "password2",
                "코난",
                "conan@nextstep.camp"
        );

        // When, Then
        String cookie = client.getLoginCookie(loginDto);

        client.post("/users/update")
                .header("cookie", cookie)
                .body(fromFormData("userId", loginDto.getUserId())
                        .with("password", updatedUser.getPassword())
                        .with("name", updatedUser.getName())
                        .with("email", updatedUser.getEmail()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/");
    }

    private void createUserForLogin(String userId, String password) {
        UserCreatedDto userCreatedDto = new UserCreatedDto(
                userId,
                password,
                "name",
                "email@email.com"
        );

        client.post("/users/create")
                .body(fromFormData("userId", userCreatedDto.getUserId())
                        .with("password", userCreatedDto.getPassword())
                        .with("name", userCreatedDto.getName())
                        .with("email", userCreatedDto.getEmail()))
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader()
                .valueEquals("location", "/");
    }

}