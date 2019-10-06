package nextstep.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DispatcherServletTest {
    private DispatcherTest dispatcherTest;

    @BeforeEach
    void setUp() {
        dispatcherTest = new DispatcherTest();
    }

    @Test
    void 유저생성() throws ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "admin");
        body.put("password", "password");

        dispatcherTest.post("/users/create", body).service();
        assertThat(dispatcherTest.getResponse().getRedirectedUrl()).isEqualTo("/");
    }

    @Test
    void 유저프로필_조회() throws ServletException {
        dispatcherTest.get("/users/profile?userId=admin").service();
        assertThat(dispatcherTest.getResponse().getForwardedUrl()).isEqualTo("/user/profile.jsp");
    }

    @Test
    void 유저리스트_조회() throws ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("userId", "admin");
        body.put("password", "password");

        DispatcherTest loginDispatcher = new DispatcherTest();
        loginDispatcher.post("/users/login", body).service();

        dispatcherTest.get("/users")
                .setSession(loginDispatcher.getSession())
                .service();

        assertThat(dispatcherTest.getResponse().getForwardedUrl()).isEqualTo("/user/list.jsp");
    }
}