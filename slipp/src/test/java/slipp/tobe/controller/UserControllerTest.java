package slipp.tobe.controller;

import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import slipp.domain.User;
import slipp.support.db.DataBase;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        User user = new User("1","password", "robby", "robby@naver.com");
        DataBase.addUser(user);
        EntityExchangeResult<byte[]> result = nsWebTestClient.getResponse("/users?userId=1");
        String response = result.toString();
        assertThat(response).contains("robby");
    }
}