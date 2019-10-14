package nextstep.web.support;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE, ALL;

    private static final String NOT_SUPPORTED_METHOD_ERROR = "지원하지 않는 메서드입니다.";

    public static RequestMethod resolve(String method) {
        for (RequestMethod requestMethod : RequestMethod.values()) {
            if (requestMethod.name().equals(method.toUpperCase())) {
                return requestMethod;
            }
        }
        throw new NotSupportedMethodException(NOT_SUPPORTED_METHOD_ERROR);
    }

    public List<RequestMethod> getMethods() {
        if (this == ALL) {
            return Arrays.stream(values())
                    .filter(method -> method != ALL)
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(this);
    }
}
