package nextstep.mvc.tobe;

import nextstep.mvc.exception.NotFoundAdapterException;

import java.util.List;

public class AdapterManager {
    private List<Adapter> adapters;

    public AdapterManager(List<Adapter> adapters) {
        this.adapters = adapters;
    }

    public Adapter getHandlerAdapter(Object handler) {
        for (Adapter adapter : adapters) {
            if (adapter.isSupport(handler)) {
                return adapter;
            }
        }

        throw new NotFoundAdapterException();
    }
}
