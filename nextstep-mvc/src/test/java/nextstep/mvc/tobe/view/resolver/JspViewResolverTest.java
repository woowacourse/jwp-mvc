package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.view.JspView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JspViewResolverTest {
    @Test
    void viewName이_jsp일때_리턴값_테스트() {
        JspViewResolver jspViewResolver = new JspViewResolver();

        assertThat(jspViewResolver.resolveViewName("a.jsp")).isInstanceOf(JspView.class);
    }

    @Test
    void viewName이_jsp가_아닐때_리턴값_테스트() {
        JspViewResolver jspViewResolver = new JspViewResolver();

        assertThat(jspViewResolver.resolveViewName("index.html")).isNull();
    }
}