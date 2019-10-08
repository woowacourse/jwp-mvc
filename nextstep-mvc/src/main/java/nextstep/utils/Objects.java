package nextstep.utils;

public class Objects {
    private Objects() {
    }

    public static boolean isNull(final Object object) {
        return object == null;
    }

    public static boolean isNotNull(final Object object) {
        return !isNull(object);
    }
}
