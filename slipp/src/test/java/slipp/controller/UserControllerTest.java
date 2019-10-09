package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class UserControllerTest {
    private static final String LOCATION = "Location";

    private NsWebTestClient client;
    private MultiValueMap<String, String> multiValueMap;

    @BeforeEach
    @DisplayName("회원가입")
    void setUp() {
        client = NsWebTestClient.of(8080);

        multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "pobi");
        multiValueMap.add("password", "password");
        multiValueMap.add("name", "pobi");
        multiValueMap.add("email", "pobi@nextstep.camp");

        client.postRequest("/users/create", multiValueMap)
                .isFound()
                .expectHeader()
                .valueEquals(LOCATION, "/");
    }

    @Test
    void 회원가입_페이지_이동() {
        client.getRequest("/users/form")
                .isOk();
    }

    @Test
    void 로그인_성공() {
        client.postRequest("/users/login", multiValueMap)
                .isFound()
                .expectHeader()
                .valueEquals(LOCATION, "/");
    }

    @Test
    void 로그인_실패_회원이_없을때() {
        multiValueMap.clear();
        client.postRequest("/users/login", multiValueMap)
                .isOk();
    }

    @Test
    void 로그인_실패_비밀번호가_일치하지_않을때() {
        multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "pobi");
        multiValueMap.add("password", "wrong_password");
        multiValueMap.add("name", "pobi");
        multiValueMap.add("email", "pobi@nextstep.camp");

        client.postRequest("/users/login", multiValueMap)
                .isOk();
    }

    @Test
    void 로그인시_회원_리스트_페이지_이동() {
        client.loginRequest("/users", HttpMethod.GET, multiValueMap)
                .isOk();
    }

    @Test
    void 비로그인시_회원_리스트_페이지_이동() {
        client.getRequest("/users")
                .isFound()
                .expectHeader()
                .valueEquals(LOCATION, "/users/loginForm");
    }

    @Test
    void 회원_프로필_조회() {
        client.getRequest("/users/profile?userId=pobi")
                .isOk();
    }

    @Test
    void 회원_프로필_조회_실패() {
        client.getRequest("/users/profile?userId=nullpointexception")
                .is5xxServerError();
    }

    @Test
    void 로그아웃() {
        client.getRequest("/users/logout").isFound();
    }

    @Test
    void 올바른_사용자_업데이트_페이지_이동() {
        client.loginRequest("/users/updateForm?userId=pobi", HttpMethod.GET, multiValueMap)
                .isOk();
    }

    @Test
    void 다른_사용자_업데이트_페이지_이동() {
        client.loginRequest("/users/updateForm?userId=admin", HttpMethod.GET, multiValueMap)
                .is5xxServerError();
    }

    @Test
    void 올바른_사용자_업데이트() {
        MultiValueMap<String, String> updateData = new LinkedMultiValueMap<>();

        updateData.add("userId", "pobi");
        updateData.add("password", "p@ssw0rd");
        updateData.add("name", "mir");
        updateData.add("email", "ddu0422@naver.com");

        client.loginAndBodyRequest("/users/update", HttpMethod.POST, multiValueMap, updateData)
                .isFound()
                .expectHeader()
                .valueEquals(LOCATION, "/");
    }

    @Test
    void 다른_사용자_업데이트() {
        MultiValueMap<String, String> updateData = new LinkedMultiValueMap<>();

        updateData.add("userId", "mir");
        updateData.add("password", "p@ssw0rd");
        updateData.add("name", "mir");
        updateData.add("email", "ddu0422@naver.com");

        client.loginAndBodyRequest("/users/update", HttpMethod.POST, multiValueMap, updateData)
                .is5xxServerError();
    }
}