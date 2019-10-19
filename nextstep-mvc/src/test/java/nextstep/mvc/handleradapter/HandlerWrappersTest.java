package nextstep.mvc.handleradapter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HandlerWrappersTest {

    @Test
    @DisplayName("등록 되지 않은 클래스")
    void wrap_notAddedClass() {
        HandlerAdapterWrappers wrappers = HandlerAdapterWrappers.builder().build();

        Object notAddedHandler = new Object();
        assertThat(wrappers.wrap(notAddedHandler)).isEmpty();
    }

    @Test
    @DisplayName("등록된 클래스, 올바른 어댑터 반환")
    void wrap_addedClass() {
        Handler expectedAdapter = mock(Handler.class);
        HandlerAdapterWrappers wrappers = HandlerAdapterWrappers.builder()
                .addWrapper(Parent.class, parent -> expectedAdapter)
                .build();

        assertThat(wrappers.wrap(new Parent()).get()).isEqualTo(expectedAdapter);
    }

    @Test
    @DisplayName("등록된 클래스의 자식 클래스, 부모클래스의 어댑터 반환")
    void wrap_addedParentClass() {
        Handler expectedAdapter = mock(Handler.class);
        HandlerAdapterWrappers wrappers = HandlerAdapterWrappers.builder()
                .addWrapper(Parent.class, parent -> expectedAdapter)
                .build();

        assertThat(wrappers.wrap(new Child()).get()).isEqualTo(expectedAdapter);
    }

    private class Parent {
    }

    private class Child extends Parent {
    }
}