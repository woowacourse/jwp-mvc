package nextstep.web.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseLocationBuilderTest {

    @Test
    void of() {
        assertThat(ResponseLocationBuilder.of("/api/users")).isInstanceOf(ResponseLocationBuilder.class);
    }

    @Test
    void appendParam_and_build() {
        String location = ResponseLocationBuilder.of("/api/users")
                .appendParam("name", "value")
                .appendParam("name2", "value2")
                .build();

        assertThat(location).isEqualTo("/api/users?name=value&name2=value2");
    }
}