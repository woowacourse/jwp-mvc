package nextstep.utils;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.User;
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

    @Test
    void toJsonString() {
        User user1 = new User("pobi", "password", "포비", "pobi@nextstep.camp");
        User user2 = new User("coogie", "password", "쿠기", "coogiei@nextstep.camp");

        Map<String, User> model = new HashMap<>();
        model.put("user1", user1);
        model.put("user2", user2);

        String content = JsonUtils.toJsonString(model);

        assertThat(content.contains("pobi")).isTrue();
        assertThat(content.contains("coogie")).isTrue();
    }
}
