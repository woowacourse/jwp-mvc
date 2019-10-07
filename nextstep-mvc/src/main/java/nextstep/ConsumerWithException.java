package nextstep;

@FunctionalInterface
public interface ConsumerWithException<T ,E extends Exception> {
    void apply(T t) throws E;
}
