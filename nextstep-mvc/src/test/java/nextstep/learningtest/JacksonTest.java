package nextstep.learningtest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {

    private static final Logger log = LoggerFactory.getLogger(JacksonTest.class);
    private static final String TARGET_PATH = "src/test/java/nextstep/learningtest/target";
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Java Object를 JSON으로 변환하기")
    void writeValue() throws IOException {
        Car car = new Car("red", "renault");

        String path = TARGET_PATH + "/red_car.json";
        File resultFile = new File(path);
        objectMapper.writeValue(resultFile, car);

        printFile(path);
    }

    @Test
    @DisplayName("JSON을 Java Object로 변환하기")
    void readValue() throws IOException {
        Car car = objectMapper.readValue(new File(TARGET_PATH + "/blue_car.json"), Car.class);

        assertThat(car.getColor()).isEqualTo("blue");
        assertThat(car.getType()).isEqualTo("BMW");
        log.info("JSON to Java Object >>> car={}", car);
    }

    @Test
    @DisplayName("JSON을 jsonNode로 변환하기")
    void readTree() throws IOException {
        JsonNode jsonNode = objectMapper.readTree(new File(TARGET_PATH + "/black_car.json"));

        assertThat(jsonNode.get("color").asText()).isEqualTo("black");
        assertThat(jsonNode.get("none")).isNull();
    }

    @Test
    @DisplayName("JSON을 list로 변환하기")
    void readList() throws IOException {
        List<Car> cars = objectMapper.readValue(
                new File(TARGET_PATH + "/json_car_array.json"),
                new TypeReference<List<Car>>() {
                });

        assertThat(cars.size()).isEqualTo(2);
        log.info("JSON to list >>> cars={}", cars);
    }

    @Test
    @DisplayName("JSON을 map으로 변환하기")
    void readMap() throws IOException {
        Map<String, Object> cars = objectMapper.readValue(
                new File(TARGET_PATH + "/black_car.json"),
                new TypeReference<Map<String, Object>>() {
                });

        assertThat(cars.get("color")).isEqualTo("black");
        assertThat(cars.get("type")).isEqualTo("FIAT");
        log.info("JSON to map >>> cars={}", cars);
    }

    private void printFile(String path) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(path))) {
            byte[] buffer = new byte[8192];
            for (int length; (length = input.read(buffer)) != -1; ) {
                System.out.write(buffer, 0, length);
            }
        }
    }
}
