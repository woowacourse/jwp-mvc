package slipp.controller;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import slipp.domain.User;

import java.io.File;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private static WebTestClient.Builder testClientBuilder = WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:8080");

    private static User signedUpUser = new User("kjm", "password", "김정민", "kjm@gmail.com");
    private static User notSignedUpUser = new User("not a user", "not a password", "not a name", "no@gmail.com");


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
        byte[] contents = getPageResource("/", "not a cookie")
                .isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();

        assertThat(new String(contents))
                .contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUpTest() {
        signUp(signedUpUser.getUserId(), signedUpUser.getPassword(), signedUpUser.getName(), signedUpUser.getEmail())
                .is3xxRedirection();
    }

    @Test
    @DisplayName("로그인 테스트")
    void logIn() {
        signUp(signedUpUser.getUserId(), signedUpUser.getPassword(), signedUpUser.getName(), signedUpUser.getEmail())
                .is3xxRedirection();

        logIn(signedUpUser.getUserId(), signedUpUser.getPassword())
                .is3xxRedirection();
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void logInFail() {
        signUp(signedUpUser.getUserId(), signedUpUser.getPassword(), signedUpUser.getName(), signedUpUser.getEmail())
                .is3xxRedirection();

        byte[] contents = logIn(notSignedUpUser.getUserId(), notSignedUpUser.getPassword())
                .isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();

        assertThat(new String(contents))
                .contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
    }

    @Test
    @DisplayName("로그인 성공후 유저리스트 가져오기 테스트")
    void getUserList() {
        signUp(signedUpUser.getUserId(), signedUpUser.getPassword(), signedUpUser.getName(), signedUpUser.getEmail())
                .is3xxRedirection();

        MultiValueMap<String, ResponseCookie> cookies = logIn("kjm", "password")
                .is3xxRedirection()
                .returnResult(String.class)
                .getResponseCookies();

        String sessionId = extractSessionId(cookies);

        byte[] contents = getPageResource("/users", sessionId)
                .isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();
        String contentsAsString = new String(contents);

        assertThat(contentsAsString).contains(signedUpUser.getEmail());
        assertThat(contentsAsString).contains(signedUpUser.getUserId());
    }


    @Test
    @DisplayName("로그인 실패 후 유저리스트 가져오기 테스트 실패")
    void getUserListFail() {
        signUp(signedUpUser.getUserId(), signedUpUser.getPassword(), signedUpUser.getName(), signedUpUser.getEmail())
                .is3xxRedirection();

        byte[] failedLogInContents = logIn(notSignedUpUser.getUserId(), notSignedUpUser.getPassword())
                .isOk()
                .expectBody()
                .returnResult()
                .getResponseBody();

        String failedLogInContentsAsString = new String(failedLogInContents);

        assertThat(failedLogInContentsAsString).contains("사용자 아이디");
    }

    StatusAssertions getPageResource(String location, String cookie) {
        return testClientBuilder
                .responseTimeout(Duration.ofMillis(30000))
                .build()
                .get()
                .uri(location)
                .header("Cookie", cookie)
                .exchange()
                .expectStatus();
    }

    StatusAssertions signUp(String userId, String password, String name, String email) {
        return testClientBuilder
                .responseTimeout(Duration.ofMillis(30000))
                .build()
                .post()
                .uri("/users/create")
                .body(BodyInserters.fromFormData("userId", userId)
                        .with("password", password)
                        .with("name", name)
                        .with("email", email))
                .exchange()
                .expectStatus();
    }


    StatusAssertions logIn(String id, String password) {
        return testClientBuilder
                .responseTimeout(Duration.ofMillis(30000))
                .build()
                .post()
                .uri("/users/login")
                .body(BodyInserters.fromFormData("userId", id)
                        .with("password", password))
                .exchange()
                .expectStatus();
    }

    private String extractSessionId(MultiValueMap<String, ResponseCookie> cookies) {
        cookies.keySet().stream().forEach(key -> logger.debug("this is the key ==>" + key + " // this is the cookie ==> " + cookies.get(key)));
        String sessionId = cookies.get("JSESSIONID").toString();

        return sessionId.substring(1, sessionId.length() - 1);
    }
}
