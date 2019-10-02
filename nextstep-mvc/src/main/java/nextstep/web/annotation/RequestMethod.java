package nextstep.web.annotation;

import java.util.Arrays;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod of(String requestMethod) {
        return Arrays.stream(RequestMethod.values())
                .filter(method -> method.name().equals(requestMethod.toUpperCase()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
