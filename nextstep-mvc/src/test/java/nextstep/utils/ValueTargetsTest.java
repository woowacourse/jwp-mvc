package nextstep.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ValueTargetsTest {

    @Test
    void exist() {
        ValueTargets targets = ValueTargets.from(new HashMap<String, Class<?>>(){{
            put("name", String.class);
        }});

        assertThat(targets.exist("name", String.class)).isTrue();
    }
}