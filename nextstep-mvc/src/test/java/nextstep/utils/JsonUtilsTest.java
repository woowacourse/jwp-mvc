package nextstep.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import nextstep.mvc.mock.Car;
import nextstep.mvc.mock.User;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    @Test
    void toObject() throws Exception {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = JsonUtils.toObject(json, Car.class);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }

    @DisplayName("크기가 1인 model을 Json으로 변환")
    @Test
    void toJson_1sizeModel_equalToUserJson() throws JsonProcessingException {
        Map<String,Object> model;
        ObjectMapper objectMapper = new ObjectMapper();
        model = new HashMap<String,Object>();
        User user = new User("id","password","name","email");
        model.put("user",user);
        String json = JsonUtils.toJson(model);
        assertThat(json).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @DisplayName("크기가 0인 model을 Json으로 변환")
    @Test
    void toJson_0sizeModel_empty() throws JsonProcessingException {
        Map<String,Object> model;
        ObjectMapper objectMapper = new ObjectMapper();
        model = new HashMap<String,Object>();
        String json = JsonUtils.toJson(model);
        assertThat(json).isEqualTo(StringUtils.EMPTY);
    }

    @DisplayName("크기가 1 초과인 model을 Json으로 변환")
    @Test
    void toJson_over1sizeModel_equalToUsersJson() throws JsonProcessingException {
        Map<String,Object> model;
        ObjectMapper objectMapper = new ObjectMapper();
        model = new HashMap<String,Object>();
        User user = new User("id","password","name","email");
        model.put("user1",user);
        model.put("user2",user);
        model.put("user3",user);
        String json = JsonUtils.toJson(model);
        assertThat(json).isEqualTo(objectMapper.writeValueAsString(model));
    }
}
