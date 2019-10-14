package slipp.controller;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import support.test.NsWebTestClient;

import java.io.File;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAcceptanceTest {
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
    @DisplayName("사용자 회원가입/조회/수정/삭제")
    void crud() {
        // 회원가입
        UserCreatedDto expected =
                new UserCreatedDto("pobi", "password", "포비", "pobi@nextstep.camp");
        URI location = client.createResource("/api/users", expected, UserCreatedDto.class);
//        URI location = client.createResource("/users/create", expected, UserCreatedDto.class);
        logger.debug("location : {}", location); // /api/users?userId=pobi 와 같은 형태로 반환

        // 조회
        User actual = client.getResource(location, User.class);
        assertThat(actual.getUserId()).isEqualTo(expected.getUserId());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());

        // 수정
        UserUpdatedDto updateUser = new UserUpdatedDto("password2", "코난", "conan@nextstep.camp");
        client.updateResource(location, updateUser, UserUpdatedDto.class);

        actual = client.getResource(location, User.class);
        assertThat(actual.getPassword()).isEqualTo(updateUser.getPassword());
        assertThat(actual.getName()).isEqualTo(updateUser.getName());
        assertThat(actual.getEmail()).isEqualTo(updateUser.getEmail());
    }
}
