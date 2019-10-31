package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Disabled
public class ObjectMapperTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Java Object로부터 JSON String을 만든다.")
    void writeValueAsString() throws JsonProcessingException {
        Car car = new Car("red", "sonata");

        String carAsString = mapper.writeValueAsString(car);
        assertThat(carAsString).isEqualTo("{\"color\":\"red\",\"type\":\"sonata\"}");
    }

    @Test
    @DisplayName("JSON String으로부터 Java Object를 만든다. Object에 기본 생성자가 있어야 한다.")
    void readValue() throws IOException {
        String color = "Black";
        String type = "BMW";
        String json = "{ \"color\" : \"" + color + "\", \"type\" : \"" + type + "\" }";

        Car mapped = mapper.readValue(json, Car.class);

        assertThat(mapped.getColor()).isEqualTo(color);
        assertThat(mapped.getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("JSON String을 JsonNode로 parsing해서 data를 찾아올 수 있다.")
    void jsonNode_get() throws IOException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = mapper.readTree(json);

        String color = jsonNode.get("color").asText();

        assertThat(color).isEqualTo("Black");
    }

    @Test
    @DisplayName("JSON String에 배열로 데이터가 들어있는 경우 Java object list로 만든다.")
    void readValue_whenJSON_hasArrayData() throws IOException {
        String jsonCarArray = "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, " +
                "{ \"color\" : \"Red\", \"type\" : \"FIAT\" }]";

        List<Car> cars = mapper.readValue(jsonCarArray, new TypeReference<List<Car>>() {
        });

        assertThat(cars.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("JSON String의 데이터를 Map으로 만든다.")
    void readValue_AsMap() throws IOException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Map<String, Object> map = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
        });

        assertThat(map.size()).isEqualTo(2);
        assertThat(map.get("color")).isEqualTo("Black");
        assertThat(map.get("type")).isEqualTo("BMW");
    }

    @Test
    @DisplayName("JSON String의 field가 Java Object와 맞지 않으면 UnrecognizedPropertyException")
    void readValue_ifJSONfield_doesNotMatchObjectFields() throws IOException {
        String jsonString = "{ \"color\" : \"Black\", " +
                "\"type\" : \"Fiat\", \"year\" : \"1970\" }";

        assertThatThrownBy(() -> mapper.readValue(jsonString, Car.class))
                .isInstanceOf(UnrecognizedPropertyException.class);
    }

    @Test
    @DisplayName("JSON String의 field가 java Object와 맞지 않으면 무시하고 맞는 것만 매핑")
    void configure() throws IOException {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String jsonString = "{ \"color\" : \"Black\", " +
                "\"type\" : \"Fiat\", \"year\" : \"1970\" }";

        Car car = mapper.readValue(jsonString, Car.class);
        JsonNode jsonNode = mapper.readTree(jsonString);
        JsonNode year = jsonNode.get("year");

        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("Fiat");
        assertThat(year.asText()).isEqualTo("1970");
    }
}
