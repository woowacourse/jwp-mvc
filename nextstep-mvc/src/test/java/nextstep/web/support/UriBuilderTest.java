package nextstep.web.support;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UriBuilderTest {

    @Test
    void of() {
        assertThat(UriBuilder.of("/api/users")).isInstanceOf(UriBuilder.class);
    }

    @Test
    void buildWithoutAppendingParam() {
        String location = UriBuilder.of("/api/users")
                .build();

        assertThat(location).isEqualTo("/api/users");
    }

    @Test
    void appendParamAndBuild() {
        String location = UriBuilder.of("/api/users")
                .appendParam("name", "value")
                .appendParam("name2", "value2")
                .build();

        assertThat(location).isEqualTo("/api/users?name=value&name2=value2");
    }
}