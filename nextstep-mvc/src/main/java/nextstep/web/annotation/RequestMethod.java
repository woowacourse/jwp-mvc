package nextstep.web.annotation;

import nextstep.mvc.exception.NotFoundHttpMethodException;

import java.util.Arrays;

public enum RequestMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    ALL("ALL");

    private final String method;

    RequestMethod(String method) {
        this.method = method;
    }

    public boolean isAll() {
        return this.equals(ALL);
    }

    public static RequestMethod of(String methodType) {
        return Arrays
                .stream(values())
                .filter(m -> methodType.equals(m.method))
                .findFirst()
                .orElseThrow(NotFoundHttpMethodException::new);
    }
}
