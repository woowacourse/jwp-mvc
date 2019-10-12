package nextstep.mvc.exception;

@FunctionalInterface
public interface ExceptionFunction<T, R, E extends Exception> {
    R apply(T t) throws E;
}
