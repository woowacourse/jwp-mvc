package slipp.tobe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        NsWebTestServer nsWebTestServer = new NsWebTestServer(8080);
        nsWebTestServer.start();
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void loginForm() {
        nsWebTestClient.responseOk("/users/loginForm");
    }

    @Test
    void login() {
        Map<String, String> params = new HashMap<>();
        params.put("userId","admin");
        params.put("password", "password");
        assertThat(nsWebTestClient.postForm("/users/login", params)).isEqualTo(URI.create("/"));
    }

    @Test
    void login_fail() {
        Map<String, String> params = new HashMap<>();
        params.put("userId", "admin");
        params.put("password", "password!@");
        nsWebTestClient.postResponse("/users/login", params);
    }
}
