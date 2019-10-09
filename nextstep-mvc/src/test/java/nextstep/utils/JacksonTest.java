package nextstep.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.Car;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {

    @Test
    void objectToJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Car car = new Car("yellow", "renault");
        String actual = objectMapper.writeValueAsString(car);

        String expected = "{\"color\":\"yellow\",\"type\":\"renault\"}";

        assertThat(actual).isEqualTo(expected);
    }
}
