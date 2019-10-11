package nextstep.mvc.tobe.viewResolver;

import nextstep.mvc.tobe.view.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverManagerTest {

    private ViewResolverManager viewResolverManager;

    @BeforeEach
    void setUp() {
        viewResolverManager = new ViewResolverManager();
    }

    @Test
    void findJspViewResolver() throws NotFoundViewResolverException {
        ModelAndView mv = new ModelAndView("/users.jsp");
        assertThat(viewResolverManager.findViewResolver(mv).getClass()).isEqualTo(JspViewResolver.class);
    }

    @Test
    void findJsonViewResolver() throws NotFoundViewResolverException {
        ModelAndView mv = new ModelAndView("JsonView");
        assertThat(viewResolverManager.findViewResolver(mv).getClass()).isEqualTo(JsonViewResolver.class);
    }

    @Test
    void findRedirectViewResolver() throws NotFoundViewResolverException {
        ModelAndView mv = new ModelAndView("redirect:/");
        assertThat(viewResolverManager.findViewResolver(mv).getClass()).isEqualTo(RedirectViewResolver.class);
    }
}