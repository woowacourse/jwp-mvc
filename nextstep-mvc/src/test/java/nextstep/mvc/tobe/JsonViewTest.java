package nextstep.mvc.tobe;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.View;
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

    private MockHttpServletRequest req;
    private MockHttpServletResponse res;
    private View view;

    @BeforeEach
    void setUp() {
        this.req = new MockHttpServletRequest();
        this.res = new MockHttpServletResponse();
        this.view = new JsonView();
    }

    @Test
    void renderNoElement() throws Exception {
        this.view.render(new HashMap<>(), this.req, this.res);
        assertThat(this.res.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(this.res.getContentAsString()).isBlank();
    }

    @Test
    void renderSingleElement() throws Exception {
        final Map<String, Object> model = new HashMap<>();
        final Car expected = new Car("Black", "Sonata");
        model.put("car", expected);

        this.view.render(model, this.req, this.res);

        final Car actual = JsonUtils.toObject(this.res.getContentAsString(), Car.class);
        assertThat(this.res.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void renderMultipleElements() throws Exception {
        final Map<String, Object> model = new HashMap<>();
        final Car expected = new Car("Black", "Sonata");
        model.put("car", expected);
        model.put("name", "포비");

        this.view.render(model, this.req, this.res);

        assertThat(this.res.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
        logger.debug("this.response body : {}", this.res.getContentAsString());
    }
}