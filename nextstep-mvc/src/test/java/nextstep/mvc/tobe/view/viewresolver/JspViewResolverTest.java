package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ViewType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JspViewResolverTest {
    private JspViewResolver jspViewResolver;

    @BeforeEach
    void setUp() {
        jspViewResolver = new JspViewResolver();
    }

    @Test
    @DisplayName("support() Success")
    void supportSuccess() {
        assertThat(jspViewResolver.support(ViewType.JSP_VIEW)).isTrue();
    }

    @Test
    @DisplayName("support() Fail")
    void supportFail() {
        assertThat(jspViewResolver.support(ViewType.JSON_VIEW)).isFalse();
    }

    @Test
    @DisplayName("resolve Ok")
    void resolveTest() {
        assertThat(jspViewResolver.resolve("/index.jsp")).isInstanceOf(JspView.class);
    }
}