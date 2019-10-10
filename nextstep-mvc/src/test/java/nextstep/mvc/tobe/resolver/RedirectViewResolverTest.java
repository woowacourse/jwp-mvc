package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.view.RedirectView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewResolverTest {

    @Test
    void viewName이_redirect일때_리턴값_테스트() {
        RedirectViewResolver redirectViewResolver = new RedirectViewResolver();

        assertThat(redirectViewResolver.resolveViewName("redirect:/a.jsp")).isInstanceOf(RedirectView.class);
    }

    @Test
    void viewName이_redirect가_아닐때_리턴값_테스트() {
        RedirectViewResolver redirectViewResolver = new RedirectViewResolver();

        assertThat(redirectViewResolver.resolveViewName("index.html")).isNull();
    }

}