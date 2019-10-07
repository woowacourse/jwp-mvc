package nextstep.mvc.tobe.resolver;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MethodParametersTest {

    @Test
    void 생성_테스트() throws NoSuchMethodException {
        // given
        final Class<MethodParametersTest> clazz = MethodParametersTest.class;
        final Method method = Arrays.stream(clazz.getDeclaredMethods())
                .filter(x -> x.getName().equals("sampleMethod"))
                .findFirst()
                .get();

        // when
        final MethodParameters methodParameters = new MethodParameters(method);
        final List<MethodParameter> methodParams = methodParameters.getMethodParams();
        final MethodParameter methodParameter = methodParams.get(0);

        // then
        assertThat(methodParameter.getName()).isEqualTo("args1");
        assertThat(methodParameter.getParameter().getType()).isEqualTo(String.class);
        assertThat(methodParameter.getIndex()).isEqualTo(0);
    }

    void sampleMethod(String args1, int args2, Long args3) {

    }
}