package nextstep.web.annotation;

public enum RequestMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    public static RequestMethod resolve(String method) {
        for (RequestMethod requestMethod : RequestMethod.values()) {
            if (requestMethod.name().equals(method.toUpperCase())) {
                return requestMethod;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 메서드입니다.");
    }
}
