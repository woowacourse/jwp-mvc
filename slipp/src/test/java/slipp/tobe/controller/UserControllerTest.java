package slipp.tobe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        NsWebTestServer nsWebTestServer = new NsWebTestServer(8080);
        nsWebTestServer.start();
        nsWebTestClient = NsWebTestClient.of(8080);
        nsWebTestClient.login("admin", "password");
    }

    @Test
    void getUserListTest() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users");
        String response = result.toString();
        assertThat(response).contains("자바지기");
    }

    @Test
    void signUpTest() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("name", "무민");
        params.put("password", "password");
        params.put("email", "moomin@woowahan.com");
        assertThat(nsWebTestClient.postForm("/users", params).getResponseHeaders().getLocation()).isEqualTo(URI.create("/"));
    }

    @Test
    void updateFormTest() {
        nsWebTestClient.login("admin", "password");
        String result = nsWebTestClient.getResponse("/users/update?userId=admin").toString();
        assertThat(result).contains("사용자 아이디");
        assertThat(result).contains("admin");
    }

    @Test
    void loginFormTest() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users/login");
        assertThat(result.getStatus()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void loginSuccessTest() {
        URI redirectUrl = login("admin", "password");
        assertThat(redirectUrl.toString()).isEqualTo("/");
    }

    @Test
    void logoutSuccessTest() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users/logout");
        assertThat(result.getStatus().is3xxRedirection()).isTrue();
    }

    @Test
    void profileTest() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users/profile?userId=admin");
        String response = result.toString();
        assertThat(response).contains("자바지기");
    }

    private URI login(String userId, String password) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("password", password);
        return nsWebTestClient.postForm("/users/login", params).getResponseHeaders().getLocation();
    }
}