package nextstep.utils;

import nextstep.mvc.helper.Car;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    private String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";

    @Test
    public void stringToObject() throws Exception {
        Car car = JsonUtils.toObject(json, Car.class);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }

    @Test
    public void inputStreamToObject() {
        InputStream inputStream = new ByteArrayInputStream(json.getBytes());

        Car car = JsonUtils.toObject(inputStream, Car.class);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }
}
