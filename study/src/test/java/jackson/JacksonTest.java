package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonTest {
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void jsonToObject() throws IOException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"Sonata\" }";
        Car actual = mapper.readValue(json, Car.class);

        Car expect = new Car("Black", "Sonata");

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void jsonToJsonNode() throws IOException {
        String json = "{ \"color\" : \"Black\", \"type\" : \"Sonata\" }";
        JsonNode jsonNode = mapper.readTree(json);
        String color = jsonNode.get("color").asText();

        assertThat(color).isEqualTo("Black");
    }

    @Test
    void objectToJson() throws JsonProcessingException {
        Car car = new Car("Black", "Sonata");
        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(car);

        String expect =
                "{\n" +
                "  \"color\" : \"Black\",\n" +
                "  \"type\" : \"Sonata\"\n" +
                "}";

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void mapObjectToJson() throws JsonProcessingException {
        Map<String, Object> models = Maps.newHashMap();
        models.put("car", new Car("Black", "Sonata"));
        models.put("name", "포비");

        String actual = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(models);

        String expect =
                "{\n" +
                "  \"car\" : {\n" +
                "    \"color\" : \"Black\",\n" +
                "    \"type\" : \"Sonata\"\n" +
                "  },\n" +
                "  \"name\" : \"포비\"\n" +
                "}";

        assertThat(actual).isEqualTo(expect);
    }
}
