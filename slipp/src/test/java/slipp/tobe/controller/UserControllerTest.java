package slipp.tobe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest {
    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        NsWebTestServer nsWebTestServer = new NsWebTestServer(8080);
        nsWebTestServer.start();
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void getUserProfile() {
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users?userId=admin");
        String response = result.toString();
        assertThat(response).contains("자바지기");
    }

    @Test
    void user_form() {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("userId", "admin");
        loginParams.put("password", "password");

        String cookie = nsWebTestClient.getLoginCookie("/users/login", loginParams);
        Map<String, String> params = new HashMap<>();
        params.put("userId", "admin");

        nsWebTestClient.updateForm("/users/updateForm", params, cookie);
    }

    @Test
    void postUser() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        params.put("name", "무민");
        params.put("password", "password");
        params.put("email", "moomin@woowahan.com");
        assertThat(nsWebTestClient.postForm("/users", params)).isEqualTo(URI.create("/"));
    }
}