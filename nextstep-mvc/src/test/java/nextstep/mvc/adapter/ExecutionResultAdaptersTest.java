package nextstep.mvc.adapter;

import nextstep.mvc.exception.ExecutionResultAdapterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExecutionResultAdaptersTest {
    private ExecutionResultAdapters adapters;

    @BeforeEach
    void setUp() {
        adapters = new ExecutionResultAdapters();
        adapters.initialize();
    }

    @Test
    @DisplayName("String 처리 adapter 찾기")
    void findAdapter() {
        ExecutionResultAdapter adapter = adapters.findAdapter("String");

        assertThat(adapter).isInstanceOf(StringExecutionResultAdapter.class);
    }

    @Test
    @DisplayName("처리할 수 없는 형태 입력")
    void notFoundAdapter() {
        assertThatThrownBy(() -> adapters.findAdapter(1))
                .isInstanceOf(ExecutionResultAdapterException.class);
    }
}