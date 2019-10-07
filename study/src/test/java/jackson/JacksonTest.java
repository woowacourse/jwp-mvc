package jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {
    private static final Logger logger = LoggerFactory.getLogger(JacksonTest.class);
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void javaObjectToString() throws IOException {
        CarTest car = new CarTest("blue-black", "credos");
        String carAsString = objectMapper.writeValueAsString(car);
        logger.debug(carAsString);
    }

    @Test
    void jsonToJavaObject() throws IOException {
        String json = "{ \"color\" : \"blue-black\", \"type\" : \"stella\" }";
        CarTest car = objectMapper.readValue(json, CarTest.class);
        logger.debug(car.toString());
        assertThat(car).isEqualTo(new CarTest("blue-black", "stella"));
    }
}
