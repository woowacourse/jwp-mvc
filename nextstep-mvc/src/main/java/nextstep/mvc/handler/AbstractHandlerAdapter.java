package nextstep.mvc.handler;

import java.util.Optional;

public abstract class AbstractHandlerAdapter implements HandlerAdapter {
    private final Class<?> clazz;

    AbstractHandlerAdapter(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean apply(Object handler) {
        Optional.ofNullable(handler)
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 핸들러 입니다."));
        return clazz.isInstance(handler);
    }
}
