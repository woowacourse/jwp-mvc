package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.handler.HandlerExecution;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.ResponseBody;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ResponseBodyAdapterTest {
    private ResponseBodyAdapter adapter = new ResponseBodyAdapter();

    @Test
    void supports_ResponseBody_포함_true() throws NoSuchMethodException {
        final HandlerExecution handler = new HandlerExecution(null, Foo.class.getMethod("foo"));

        assertThat(adapter.supports(handler)).isTrue();
    }

    @Test
    void supports_ResponseBody_미포함_false() throws NoSuchMethodException {
        final HandlerExecution handler = new HandlerExecution(null, Foo2.class.getMethod("foo"));

        assertThat(adapter.supports(handler)).isFalse();
    }

    class Foo{
        @ResponseBody
        @RequestMapping
        public void foo(){};
    }

    class Foo2{
        @RequestMapping
        public void foo(){};
    }
}