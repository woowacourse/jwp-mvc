package slipp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.NsWebTestClient;

class UpdateUserControllerTest {

    private NsWebTestClient client;
    private MultiValueMap<String, String> multiValueMap;

    @BeforeEach
    void setUp() {
        client = NsWebTestClient.of(8080);
        multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("userId", "thisIsId");
        multiValueMap.add("password", "1234");
        multiValueMap.add("name", "thisIsNotUpdatedName");
        multiValueMap.add("email", "thisIsNotUpdatedMail@gmail.com");

        client.postRequest("/users/create", multiValueMap);
    }

    @Test
    void updateUser_with_logined() {
        MultiValueMap<String, String> updateValues = new LinkedMultiValueMap<>();
        updateValues.add("userId", "thisIsId");
        updateValues.add("password", "1234");
        updateValues.add("name", "thisIsUpdateName");
        updateValues.add("email", "updatedEmail@gamil.com");
        client.postRequestWithLogined("/users/update", multiValueMap, updateValues)
            .isFound();
    }

    @Test
    void updateUser_invalid_user() {
        MultiValueMap<String, String> updateValues = new LinkedMultiValueMap<>();
        updateValues.add("userId", "anotherId");
        updateValues.add("password", "4321");
        updateValues.add("name", "thisIsUpdateName");
        updateValues.add("email", "updatedEmail@gamil.com");

        client.postRequestWithLogined("/users/update", multiValueMap, updateValues)
            .is5xxServerError();
    }
}