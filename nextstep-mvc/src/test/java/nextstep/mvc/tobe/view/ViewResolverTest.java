package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.exception.NoSuchViewException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewResolverTest {

    @Test
    public void resolveRedirectView() {
        View view = ViewResolver.resolve("redirect:/users/redirect");
        assertThat(view.getClass()).isEqualTo(RedirectView.class);
    }

    @Test
    public void resolveJspView() {
        View view = ViewResolver.resolve("/home.jsp");
        assertThat(view.getClass()).isEqualTo(JspView.class);
    }

    @Test
    public void noSuchViewException() {
        assertThrows(NoSuchViewException.class, () -> ViewResolver.resolve("error"));
    }
}