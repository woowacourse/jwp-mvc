package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class CreateUserControllerTest {

    private NsWebTestClient client;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
    }

    @Test
    void userSignUp() {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "javajigi");
        multiValueMap.add("password", "hiBro");
        multiValueMap.add("name", "pobi");
        multiValueMap.add("email", "javajigi@woowa.com");

        client.postRequest("/users/create", multiValueMap)
            .is3xxRedirection();
    }
}