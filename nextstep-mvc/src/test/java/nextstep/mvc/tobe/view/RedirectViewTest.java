package nextstep.mvc.tobe.view;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class RedirectViewTest {

    @Test
    void RedirectView가_올바르게_렌더링하는지_확인() throws Exception {
        // given
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        // when
        View view = new RedirectView("redirect:/a.jsp");
        view.render(Maps.newHashMap(), request, response);

        // then
        assertThat(response.getStatus()).isEqualTo(302);
        assertThat(response.getHeader("Location")).isEqualTo("/a.jsp");
    }
}