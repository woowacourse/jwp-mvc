package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.ViewType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonViewReturnTypeResolverTest {
    private JsonViewReturnTypeResolver jsonViewReturnTypeResolver;

    @BeforeEach
    void setUp() {
        jsonViewReturnTypeResolver = new JsonViewReturnTypeResolver();
    }

    @Test
    @DisplayName("jsonViewReturnTypeResolver support() Success")
    void supportSuccess() {
        assertThat(jsonViewReturnTypeResolver.support(Car.class)).isTrue();
    }

    @Test
    @DisplayName("jsonViewReturnTypeResolver support() Fail")
    void supportFail() {
        assertThat(jsonViewReturnTypeResolver.support(new String())).isFalse();
        assertThat(jsonViewReturnTypeResolver.support(new ModelAndView())).isFalse();
    }

    @Test
    @DisplayName("jsonViewReturnTypeResolver resolve() 확인")
    void checkResolve() {
        Car handlerObject = new Car("red", "Bus");
        ModelAndView mv = jsonViewReturnTypeResolver.resolve(handlerObject);

        assertThat(mv.getViewType()).isEqualTo(ViewType.JSON_VIEW);
        assertThat(mv.getObject(handlerObject.getClass().getName())).isEqualTo(handlerObject);
        System.out.println(handlerObject.getClass().getName());
    }
}