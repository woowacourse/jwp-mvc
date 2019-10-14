package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.ViewType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class JspViewReturnTypeResolverTest {
    private JspViewReturnTypeResolver jspViewReturnTypeResolver;
    private String viewName = "/index.html";

    @BeforeEach
    void setUp() {
        jspViewReturnTypeResolver = new JspViewReturnTypeResolver();
    }

    @Test
    @DisplayName("jspViewReturnTypeResolver support() Success")
    void supportSuccess() {
        assertThat(jspViewReturnTypeResolver.support(viewName)).isTrue();
    }

    @Test
    @DisplayName("jspViewReturnTypeResolver support() Fail")
    void supportFail() {
        assertThat(jspViewReturnTypeResolver.support(new ModelAndView())).isFalse();
        assertThat(jspViewReturnTypeResolver.support(new Car("red", "Bus"))).isFalse();
    }

    @Test
    @DisplayName("jspViewReturnTypeResolver resolve() 확인")
    void checkResolve() {
        ModelAndView mv = jspViewReturnTypeResolver.resolve(viewName);

        assertThat(mv.getViewType()).isEqualTo(ViewType.JSP_VIEW);
        assertThat(mv.getViewName()).isEqualTo(viewName);
    }
}