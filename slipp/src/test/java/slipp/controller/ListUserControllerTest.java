package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class ListUserControllerTest {

    private NsWebTestClient nsWebTestClient;

    @BeforeEach
    void setUp() {
        nsWebTestClient = NsWebTestClient.of(8080);
    }

    @Test
    @DisplayName("로그인 상태에서 유저 리스트 요청")
    void findUsers_with_logined() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "javajigi");
        multiValueMap.add("password", "hiBro");
        multiValueMap.add("name", "pobi");
        multiValueMap.add("email", "javajigi@woowa.com");

        nsWebTestClient.getRequestWithLogined("/users/list", multiValueMap)
            .isOk();
    }

    @Test
    @DisplayName("비로그인 상태에서 유저 리스트 요청")
    void findUsers_without_logined() {
        nsWebTestClient.getRequest("/users/list")
            .is3xxRedirection();
    }
}