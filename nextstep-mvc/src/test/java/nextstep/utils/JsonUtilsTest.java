package nextstep.utils;

import nextstep.mvc.tobe.Car;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonUtilsTest {
    @Test
    void toObject() {
        String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContent(json.getBytes());
        Car car = JsonUtils.createObject(request, Car.class);
        assertThat(car.getColor()).isEqualTo("Black");
        assertThat(car.getType()).isEqualTo("BMW");
    }
}
