package nextstep.mvc.handler;

public abstract class AbstractHandlerAdapter implements HandlerAdapter {
    private final Class<?> clazz;

    AbstractHandlerAdapter(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean apply(Object handler) {
        return clazz.isInstance(handler);
    }
}
