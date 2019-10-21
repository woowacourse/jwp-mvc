package nextstep.utils;

import nextstep.mvc.tobe.testdata.Car;
import nextstep.mvc.tobe.testdata.User;
import org.junit.jupiter.api.Test;

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
    void toJson() throws Exception {
        Car car = new Car("Black", "BMW");

        assertThat(JsonUtils.toJson(car)).isEqualTo(removeSpace("{ \"color\" : \"Black\", \"type\" : \"BMW\" }"));
    }

    private String removeSpace(String s) {
        return s.replace(" ", "");
    }
}
