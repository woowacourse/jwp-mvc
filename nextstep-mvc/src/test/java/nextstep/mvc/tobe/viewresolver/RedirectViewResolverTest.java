package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewResolverTest {
    @Test
    void resolve() {
        String prefix = "redirect:";
        String url = "/users";
        String viewName = prefix + url;
        RedirectViewResolver redirectViewResolver = new RedirectViewResolver();

        assertThat(redirectViewResolver.supports(viewName)).isTrue();
        assertThat(redirectViewResolver.resolve(viewName)).isEqualTo(new RedirectView(url));
    }
}