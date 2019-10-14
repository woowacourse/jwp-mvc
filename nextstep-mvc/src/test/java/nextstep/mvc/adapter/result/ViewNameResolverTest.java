package nextstep.mvc.adapter.result;

import nextstep.mvc.exception.ViewNameResolveException;
import nextstep.mvc.view.JspView;
import nextstep.mvc.view.RedirectView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ViewNameResolverTest {
    @Test
    void redirectView() {
        assertThat(ViewNameResolver.resolve("redirect:test"))
                .isInstanceOf(RedirectView.class);
    }

    @Test
    void jspView() {
        assertThat(ViewNameResolver.resolve("test.jsp"))
                .isInstanceOf(JspView.class);
    }

    @Test
    void notFoundView() {
        assertThatThrownBy(() -> ViewNameResolver.resolve("invalid"))
                .isInstanceOf(ViewNameResolveException.class);
    }
}