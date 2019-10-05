package nextstep.web.annotation;

import java.util.Arrays;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private static final String WARNING_MESSAGE = "요청한 메소드가 없습니다.";

    public static RequestMethod of(String requestMethod) {
        return Arrays.stream(RequestMethod.values())
                .filter(method -> method.name().equals(requestMethod))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(WARNING_MESSAGE));
    }
}
