package slipp.controller2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.dto.UserCreatedDto;
import support.test.NsWebTestClient;

import java.util.List;

import static support.test.NsWebTestClient.JSESSIONID;

class UserControllerTests {
    private NsWebTestClient client;
    private String jSessionId;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);

        // 회원가입
        UserCreatedDto createdDto =
                new UserCreatedDto("pobi", "password", "포비", "pobi@nextstep.camp");
        client.createResource("/api/users", createdDto, UserCreatedDto.class);

        // 로그인
        jSessionId = client.getJSessionId("pobi", "password");
    }

    @Test
    void 로그인_X_유저목록_조회_redirect() {
        client.get().uri("/users")
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectHeader().valueMatches("Location", "/users/loginForm");
    }

    @Test
    void 로그인_O_유저목록_조회_성공() {
        client.get().uri("/users")
                .cookie(JSESSIONID, jSessionId)
                .exchange()
                .expectStatus().isOk();
    }
}