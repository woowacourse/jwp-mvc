package slipp.controller;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.NsWebTestClient;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotatedControllerAcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);

    private NsWebTestClient client;

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
        client = NsWebTestClient.of(8080);
    }

    @Test
    @DisplayName("홈페이지 Resources 회수 테스트")
    void homePage() {
        assertThat(client.getPageResource("/", "randomcookie"))
                .contains("국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?");
    }

    @Test
    @DisplayName("회원가입 테스트")
    void signUp() {
        client.signUp();
    }

    @Test
    @DisplayName("로그인 테스트")
    void logIn() {
        client.signUp();
        client.logIn("kjm", "password");
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void logInFail() {
        client.signUp();
        assertThat(new String(client.logInFail("noneUser", "hahahaha")))
                .contains("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
    }

    @Test
    @DisplayName("로그인 성공후 유저리스트 가져오기 테스트")
    void getUserList() {
        client.signUp();
        String cookie = client.logIn("kjm", "password");
        assertThat(client.getPageResource("/users", cookie)).contains("kjm@gmail.com");
    }

    @Test
    @DisplayName("로그인 실패 후 유저리스트 가져오기 테스트 실패")
    void getUserListFail() {
        client.signUp();
        client.getFailedPageResource("/users", "not a cookie");
    }
}
