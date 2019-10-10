package nextstep.mvc.tobe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {
    private static final Logger log = LoggerFactory.getLogger(JacksonTest.class);

    @Test
    void JSON_data_to_Object() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = objectMapper.readValue(json, Car.class);

        assertThat(objectMapper.readValue(json, Car.class)).isEqualTo(car);

        // TODO: 2019-10-10  
        String json2 = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map
                = objectMapper.readValue(json2, new TypeReference<Map<String,Object>>(){});

    }
}
