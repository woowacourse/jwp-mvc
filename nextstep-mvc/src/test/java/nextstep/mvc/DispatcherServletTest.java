package nextstep.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    private DispatcherTestHelper dispatcherTestHelper;

    @BeforeEach
    void setUp() {
        dispatcherTestHelper = new DispatcherTestHelper();
    }

    @Test
    void 유저생성() throws ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "admin");
        body.put("password", "password");

        dispatcherTestHelper.post("/users/create", body).service();
        assertThat(dispatcherTestHelper.getResponse().getRedirectedUrl()).isEqualTo("/");
    }

    @Test
    void 유저프로필_조회() throws ServletException {
        dispatcherTestHelper.get("/users/profile?userId=admin").service();
        assertThat(dispatcherTestHelper.getResponse().getForwardedUrl()).isEqualTo("/user/profile.jsp");
    }

    @Test
    void 유저리스트_조회() throws ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "admin");
        body.put("password", "password");

        DispatcherTestHelper loginDispatcher = new DispatcherTestHelper();
        loginDispatcher.post("/users/login", body).service();

        dispatcherTestHelper.get("/users")
                .setSession(loginDispatcher.getSession())
                .service();

        assertThat(dispatcherTestHelper.getResponse().getForwardedUrl()).isEqualTo("/user/list.jsp");
    }
}