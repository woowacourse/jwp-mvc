package nextstep.mvc.tobe.handler;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapterManager {
    private static final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    static {
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    public static HandlerAdapter getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny()
                .orElse(null);
    }
}
