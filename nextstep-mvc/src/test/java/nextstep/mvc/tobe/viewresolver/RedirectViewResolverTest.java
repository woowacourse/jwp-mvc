package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.Test;

import static nextstep.mvc.tobe.viewresolver.RedirectViewResolver.DEFAULT_REDIRECT_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewResolverTest {
    private RedirectViewResolver viewResolver = new RedirectViewResolver();

    @Test
    void supports_RedirectView_이면_true() {
        assertThat(viewResolver.supports(new RedirectView("foo"))).isTrue();
    }

    @Test
    void supports_prefix가_redirect_이면_true() {
        assertThat(viewResolver.supports(DEFAULT_REDIRECT_PREFIX + "/")).isTrue();
    }

    @Test
    void supports_유효_하지않은_타입_false() {
        assertThat(viewResolver.supports(new JsonView())).isFalse();
    }

    @Test
    void resolveView_String_이면_Redirect_반환() {
        assertThat(viewResolver.resolveView(DEFAULT_REDIRECT_PREFIX + "/")).isInstanceOf(RedirectView.class);

    }
}