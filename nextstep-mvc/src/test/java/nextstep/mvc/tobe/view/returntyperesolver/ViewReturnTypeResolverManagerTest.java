package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.Car;
import nextstep.mvc.tobe.ModelAndView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViewReturnTypeResolverManagerTest {
    @Test
    @DisplayName("returnType 에 해당하는 ViewResolver")
    void getViewReturnTypeResolverTest() {
        assertThat(ViewReturnTypeResolverManager.getViewReturnTypeResolver(new ModelAndView()))
                .isInstanceOf(ModelAndViewReturnTypeResolver.class);
        assertThat(ViewReturnTypeResolverManager.getViewReturnTypeResolver(new String()))
                .isInstanceOf(JspViewReturnTypeResolver.class);
        assertThat(ViewReturnTypeResolverManager.getViewReturnTypeResolver(new Car("red", "Bus")))
                .isInstanceOf(JsonViewReturnTypeResolver.class);

        assertThat(ViewReturnTypeResolverManager.getViewReturnTypeResolver(null))
                .isEqualTo(null);
    }
}