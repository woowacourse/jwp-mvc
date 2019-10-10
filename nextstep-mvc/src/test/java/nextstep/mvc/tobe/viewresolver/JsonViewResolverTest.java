package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class JsonViewResolverTest {
    private JsonViewResolver viewResolver = new JsonViewResolver();

    @Test
    void supports_JsonView_경우_true() {
        assertThat(viewResolver.supports(new JsonView())).isTrue();
    }

    @Test
    void supports_JsonView_아닌_경우_false() {
        assertThat(viewResolver.supports(new RedirectView("asd"))).isFalse();
    }
}