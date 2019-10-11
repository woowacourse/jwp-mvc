package nextstep.mvc.tobe.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import nextstep.mvc.tobe.exception.InvalidHandlerAdaptException;
import nextstep.mvc.tobe.view.exception.InvalidModelSizeException;
import nextstep.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import samples.Car;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ModelToJsonParserSelectorTest {

    private Map<String, Object> testModel;

    @Test
    void modelSize가_0인_경우_빈_String_반환_테스트() throws JsonProcessingException {
        testModel = Collections.emptyMap();
        ModelToJsonParser<Map<String, ?>, String> jsonParser = JsonParserSelector.getJsonParser(ModelSize.BLANK);

        assertThat(jsonParser.parse(testModel)).isEqualTo("");
    }

    @Test
    void modelSize가_1인_경우_value값만_반환_테스트() throws JsonProcessingException {
        testModel = Maps.newHashMap();

        Car expected = new Car("red", "suv");
        testModel.put("car", expected);

        ModelToJsonParser<Map<String, ?>, String> jsonParser = JsonParserSelector.getJsonParser(ModelSize.ONE_SIZE);
        String json = jsonParser.parse(testModel);
        Car actual = JsonUtils.toObject(json, Car.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void modelSize가_2이상인_경우_Map객체_파싱_테스트() throws IOException {
        testModel = Maps.newHashMap();

        testModel.put("car", new Car("red", "suv"));
        testModel.put("car2", new Car("blue", "suv"));

        ModelToJsonParser<Map<String, ?>, String> jsonParser = JsonParserSelector.getJsonParser(ModelSize.MANY_SIZE);
        String json = jsonParser.parse(testModel);
        ObjectMapper objectMapper = new ObjectMapper();

        assertThat(testModel).isEqualTo(objectMapper.readValue(json, new TypeReference<Map<String, Car>>(){}));
    }

    @Test
    void modelSize가_null인경우_예외_테스트() {
        assertThrows(InvalidModelSizeException.class, () -> JsonParserSelector.getJsonParser(null));
    }
}