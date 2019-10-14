package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.ModelAndView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ModelAndViewReturnTypeResolverTest {
    private ModelAndViewReturnTypeResolver modelAndViewReturnTypeResolver;
    private ModelAndView handlerResult = new ModelAndView();

    @BeforeEach
    void setUp() {
        modelAndViewReturnTypeResolver = new ModelAndViewReturnTypeResolver();
    }

    @Test
    @DisplayName("modelAndViewReturnTypeResolver support() Success")
    void supportSuccess() {
        assertThat(modelAndViewReturnTypeResolver.support(handlerResult)).isTrue();
    }

    @Test
    @DisplayName("jspViewReturnTypeResolver support() Fail")
    void supportFail() {
        assertThat(modelAndViewReturnTypeResolver.support(new String())).isFalse();
        assertThat(modelAndViewReturnTypeResolver.support(new Car("red", "Bus"))).isFalse();
        assertThat(modelAndViewReturnTypeResolver.support(null)).isFalse();
    }

    @Test
    @DisplayName("jspViewReturnTypeResolver resolve() 확인")
    void checkResolve() {
        assertThat(handlerResult).isEqualTo(modelAndViewReturnTypeResolver.resolve(handlerResult));
    }
}