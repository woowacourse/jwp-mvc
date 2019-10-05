package nextstep.mvc;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ViewResolverTest {
    @Test
    void resolveRedirectView() {
        View view = ViewResolver.resolve("redirect:/");

        assertThat(view).isInstanceOf(RedirectView.class);
    }

    @Test
    void resolveJspView() {
        View view = ViewResolver.resolve("test.jsp");

        assertThat(view).isInstanceOf(JspView.class);
    }

    @Test
    void cannotResolveView() {
        assertThrows(ViewResolveException.class,
                () -> ViewResolver.resolve("testtest"));
    }
}