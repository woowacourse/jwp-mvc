package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.JspView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class JspViewResolverTest {
    private JspViewResolver viewResolver = new JspViewResolver();

    @Test
    void supports_string_이면_true() {
        assertThat(viewResolver.supports("foo")).isTrue();
    }

    @Test
    void supports_jspView_아닌경우_false() {
        assertThat(viewResolver.supports(new JsonView())).isFalse();
    }

    @Test
    void prefix_suffix_적용_확인() {
        assertThat(viewResolver.resolveView("foo")).isEqualTo(new JspView("foo.jsp"));
    }
}