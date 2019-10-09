package slipp.tobe.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import support.test.NsWebTestClient;
import support.test.NsWebTestServer;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        NsWebTestServer nsWebTestServer = new NsWebTestServer(8080);
        nsWebTestServer.start();
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    void index() {
        EntityExchangeResult response = nsWebTestClient.getResponse("/");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.toString()).contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }
}
