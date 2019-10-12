package nextstep.mvc.tobe;

import nextstep.mvc.tobe.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JspViewTest {
    @Test
    void constructorPresenceOrAbsenceSuffix() {
        assertThat(new JspView("/index")).isEqualTo(new JspView("/index.jsp"));
    }
}