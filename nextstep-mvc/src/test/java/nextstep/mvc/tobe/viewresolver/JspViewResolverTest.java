package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JspViewResolverTest {
    @Test
    void resolve() {
        String viewName = "abc.jsp";
        JspViewResolver jspViewResolver = new JspViewResolver();

        assertThat(jspViewResolver.supports(viewName)).isTrue();
        assertThat(jspViewResolver.resolve(viewName)).isEqualTo(new JspView(viewName));
    }
}