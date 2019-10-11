package nextstep.utils;

import nextstep.mvc.tobe.Car;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    @Test
    void toObject() throws Exception {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        Car car = JsonUtils.toObject(new ByteArrayInputStream(json.getBytes()), Car.class);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }
}
