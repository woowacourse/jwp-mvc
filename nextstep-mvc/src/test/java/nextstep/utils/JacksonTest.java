package nextstep.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.Car;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {
    private static final Logger log = LoggerFactory.getLogger(JacksonTest.class);

    @Test
    void objectToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
        String actual = objectMapper.writeValueAsString(car);

        String expected = "{\"color\":\"yellow\",\"type\":\"renault\"}";

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void mapToJson() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        Car car = new Car("yellow", "renault");
        Car car2 = new Car("red", "porsche");
        map.put("car", car);
        map.put("car2", car2);

        ObjectMapper objectMapper = new ObjectMapper();
        log.debug(objectMapper.writeValueAsString(map));
    }
}
