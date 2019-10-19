package nextstep.mvc.handleradapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class HandlerAdapterWrappers {
    private static final Logger log = LoggerFactory.getLogger(HandlerAdapterWrappers.class);

    private final Map<Class<?>, Function<Object, Handler>> wrappers;

    private HandlerAdapterWrappers(Map<Class<?>, Function<Object, Handler>> wrappers) {
        this.wrappers = wrappers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<Class<?>, Function<Object, Handler>> wrappers = new HashMap<>();

        public <T> Builder addWrapper(Class<T> clazz, Function<T, Handler> wrapper) {
            wrappers.put(clazz, (Function<Object, Handler>) wrapper);
            return this;
        }

        public HandlerAdapterWrappers build() {
            return new HandlerAdapterWrappers(wrappers);
        }
    }

    public Set<Class<?>> keySet() {
        return wrappers.keySet();
    }

    public boolean canWrap(Class<?> clazz) {
        return wrappers.containsKey(clazz);
    }

    public Optional<Handler> wrap(Object handler) {
        Class<?> handlerClass = handler.getClass();
        return wrappers.keySet().stream()
                .filter(clazz -> clazz.isAssignableFrom(handlerClass))
                .map(clazz -> wrappers.get(clazz).apply(handler))
                .findFirst();
    }
}
