package slipp.controller;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import slipp.domain.User;
import support.test.NsWebTestClient;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private static WebTestClient.Builder testClientBuilder = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:8080");

    private static User user = new User("kjm", "password", "김정민", "kjm@gmail.com");

    @BeforeEach
    void setUp() {
        new Thread(() -> {
            String webappDirLocation = "webapp/";
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080);

            tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
            logger.info("configuring app with basedir: {}", new File("./" + webappDirLocation).getAbsolutePath());

            try {
                tomcat.start();
            } catch (LifecycleException e) {
                e.printStackTrace();
            }
            tomcat.getServer().await();
        }).start();
    }

    @Test
    @DisplayName("홈페이지 Resources 회수 테스트")
    void homePage() {
        assertThat(getPageResource("/", "not a cookie"))
                .contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest() {
        signUp();
    }

    @Test
    @DisplayName("로그인 테스트")
    void logIn() {
        signUp();
        logIn("kjm", "password");
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void logInFail() {
        signUp();
        assertThat(new String(logInFail("noneUser", "hahahaha")))
                .contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
    }

    @Test
    @DisplayName("로그인 성공후 유저리스트 가져오기 테스트")
    void getUserList() {
        signUp();
        String cookie = logIn("kjm", "password");
        assertThat(getPageResource("/users", cookie)).contains(user.getEmail());
        assertThat(getPageResource("/users", cookie)).contains(user.getUserId());
    }

    @Test
    @DisplayName("로그인 실패 후 유저리스트 가져오기 테스트 실패")
    void getUserListFail() {
        signUp();
        getFailedPageResource("/users", "not a cookie");
    }

     String getPageResource(String location, String cookie) {
        byte[] result = testClientBuilder.build()
                .get()
                .uri(location)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().isOk()
                .expectBody().returnResult().getResponseBody();

        return new String(result);
    }

     void signUp() {
        testClientBuilder.build()
                .post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", user.getUserId())
                        .with("password", user.getPassword())
                        .with("name", user.getName())
                        .with("email", user.getEmail()))
                .exchange()
                .expectStatus().is3xxRedirection();
    }


    String logIn(String id, String password) {
        return testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", id)
                        .with("password", password))
                .exchange()
                .expectStatus().is3xxRedirection()
                .returnResult(String.class)
                .getResponseHeaders()
                .getFirst("Set-Cookie");
    }

    byte[] logInFail(String id, String password) {
        return testClientBuilder.build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", id)
                        .with("password", password))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();
    }

    void getFailedPageResource(String location, String cookie) {
        byte[] result = testClientBuilder.build()
                .get()
                .uri(location)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus().is3xxRedirection()
                .expectBody().returnResult().getResponseBody();
    }
}
