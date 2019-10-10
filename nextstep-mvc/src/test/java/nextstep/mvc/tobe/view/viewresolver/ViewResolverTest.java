package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.*;
import nextstep.mvc.tobe.view.exception.NotSupportedViewException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slipp.domain.User;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ViewResolverTest {
    private List<ViewResolver> viewResolvers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new RedirectViewResolver());
    }

    @Test
    void jsonView() {
        User user = new User("sloth", "password", "sloth", "sloth@sloth.com");
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user);
        View view = resolveView(mav);

        assertThat(view.getClass()).isEqualTo(JsonView.class);
    }

    @Test
    void JspView() {
        ModelAndView mav = new ModelAndView("/user/list.jsp");
        View view = resolveView(mav);

        assertThat(view.getClass()).isEqualTo(JspView.class);
    }

    @Test
    void RedirectView() {
        ModelAndView mav = new ModelAndView("redirect:/");
        View view = resolveView(mav);

        assertThat(view.getClass()).isEqualTo(RedirectView.class);
    }

    private View resolveView(ModelAndView mav) {
        return viewResolvers.stream().filter(viewResolver -> viewResolver.support(mav))
                .findFirst()
                .orElseThrow(NotSupportedViewException::new)
                .resolve(mav)
                ;
    }
}