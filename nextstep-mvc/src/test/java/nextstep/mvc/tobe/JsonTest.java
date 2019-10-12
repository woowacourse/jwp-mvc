package nextstep.mvc.tobe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTest {
    private ObjectMapper objectMapper;
    private String jsonString;
    private User user;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        jsonString = "{\"userId\":\"harry\",\"password\":\"aaaa\",\"name\":\"harry\",\"email\":\"harry@naver.com\"}";
        user = new User("harry", "aaaa", "harry", "harry@naver.com");
    }

    @Test
    void JsonToJavaObject() throws IOException {
        User newUser = objectMapper.readValue(jsonString, User.class);
        System.out.println(newUser.toString());
    }


    @Test
    void JavaObjectToJson() throws JsonProcessingException {
        String jsonValue = objectMapper.writeValueAsString(user);
        assertThat(jsonValue).isEqualTo(jsonString);
    }
}
