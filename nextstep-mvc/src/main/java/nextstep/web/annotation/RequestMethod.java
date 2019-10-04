package nextstep.web.annotation;

import nextstep.mvc.exception.NotFoundHttpMethodException;

import java.util.Arrays;

public enum RequestMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    public static RequestMethod of(String methodType) {
        return Arrays
                .stream(values())
                .filter(m -> methodType.equals(m.name()))
                .findFirst()
                .orElseThrow(NotFoundHttpMethodException::new);
    }
}
