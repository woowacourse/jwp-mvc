package jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reflection.Student;

import java.util.HashMap;
import java.util.Map;

public class jacksonTest {
    private static final Logger logger = LoggerFactory.getLogger(jacksonTest.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    @Test
    void HashMapToJson() throws JsonProcessingException {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        objectMapper.writeValueAsString(map);
        logger.debug(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
    }

    @Test
    void HahMapToJsonInStudent() throws JsonProcessingException {
        Student student1 = new Student("코맥", 27);
        Student student2 = new Student("뚱이", 25);

        Map<Integer, Student> map = new HashMap<>();
        map.put(1, student1);
        map.put(2, student2);

        objectMapper.writeValueAsString(map);
        logger.debug(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map));
    }
}
