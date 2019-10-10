package nextstep.mvc.tobe.view;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class JspViewTest {

    @Test
    void JspView가_올바르게_렌더링하는지_확인() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        View view = new JspView("/user/a.jsp");
        view.render(Maps.newHashMap(), request, response);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getForwardedUrl()).isEqualTo("/user/a.jsp");
    }

}