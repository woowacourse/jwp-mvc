package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.Car;
import nextstep.utils.JsonUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonViewTest {
    private static final Logger logger = LoggerFactory.getLogger(JsonViewTest.class);
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private View view;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        view = new JsonView();
    }

    @Test
    void render_no_element() throws Exception {
        view.render(new HashMap<>(), request, response);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(response.getContentAsString()).isBlank();
    }

    @Test
    void render_one_element() throws Exception {
        Map<String, Object> model = new HashMap<>();
        Car expected = new Car("Black", "Sonata");
        model.put("car", expected);

        view.render(model, request, response);

        Car actual = JsonUtils.toObject(response.getContentAsString(), Car.class);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void render_one_primitive() throws Exception {
        Map<String, Object> model = new HashMap<>();
        int expected = 1;
        model.put("number", expected);

        view.render(model, request, response);

        int actual = JsonUtils.toObject(response.getContentAsString(), int.class);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void render_over_two_element() throws Exception {
        final Map<String, Object> model = new HashMap<>();
        final Car car = new Car("Black", "Sonata");
        final String name = "포비";
        model.put("car", car);
        model.put("name", name);

        view.render(model, request, response);

        Map actual = JsonUtils.toObject(response.getContentAsString(), Map.class);

        logger.debug("response body : {}", response.getContentAsString());
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(actual.get("name")).isEqualTo(name);
        assertThat(actual.get("car").toString()).contains(car.getColor(), car.getType());
    }
}
