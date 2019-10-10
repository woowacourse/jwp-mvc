package nextstep.mvc.adapter.result;

import nextstep.mvc.ModelAndView;
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
        adapters.add(new StringExecutionResultAdapter());
        adapters.add(new ModelAndViewExecutionResultAdapter());
    }

    @Test
    @DisplayName("String에 맞는 adapter 찾기")
    void findStringAdapter() {
        Object o = "";
        assertThat(adapters.findAdapter(o)).isInstanceOf(StringExecutionResultAdapter.class);
    }

    @Test
    @DisplayName("ModelAndView에 맞는 adapter 찾기")
    void findModelAndViewAdapter() {
        Object o = new ModelAndView();
        assertThat(adapters.findAdapter(o)).isInstanceOf(ModelAndViewExecutionResultAdapter.class);
    }

    @Test
    @DisplayName("잘못된 Object로 adapter 찾기")
    void findInvalidAdapter() {
        Object o = new Object();
        assertThatThrownBy(() -> adapters.findAdapter(o)).isInstanceOf(IllegalArgumentException.class);
    }
}