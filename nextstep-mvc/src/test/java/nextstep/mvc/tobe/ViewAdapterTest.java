package nextstep.mvc.tobe;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewAdapterTest {
    @Test
    void render_modelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        assertThat(ViewAdapter.render(modelAndView)).isInstanceOf(ModelAndView.class);
    }

    @Test
    void render_string() {
        String redirectPrefix = "redirect:";
        String view = redirectPrefix + "/home.jsp";
        assertThat(ViewAdapter.render(view)).isInstanceOf(ModelAndView.class);
    }
}