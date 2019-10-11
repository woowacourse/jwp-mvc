package nextstep.mvc.tobe.adapter;

import nextstep.mvc.HandlerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapterManager {
    private List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterManager() {
        this.handlerAdapters = new ArrayList<>();
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
    }

    public void add(HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    public HandlerAdapter findHandlerAdapter(Object handler) throws NotFoundAdapterException {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> new NotFoundAdapterException("해당하는 HandlerAdapter를 찾을 수 없습니다."));
    }
}
