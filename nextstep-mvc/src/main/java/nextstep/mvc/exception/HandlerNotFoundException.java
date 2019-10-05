package nextstep.mvc.exception;

import javax.servlet.http.HttpServletRequest;

public class HandlerNotFoundException extends RuntimeException {
    private static final String MESSAGE = "요청의 Handler를 찾을 수 없습니다.";

    public HandlerNotFoundException(HttpServletRequest request) {
        super(String.format("%s %s %s", request.getMethod(), request.getRequestURI(), MESSAGE));
    }
}
