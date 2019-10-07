package nextstep.mvc;

public class DispatcherServletException extends RuntimeException {
    private static final String DISPATCHER_SERVLET_EXCEPTION_MESSAGE = "DispatcherServlet 에서 오류가 발생했습니다.";

    public DispatcherServletException() {
        super(DISPATCHER_SERVLET_EXCEPTION_MESSAGE);
    }
}
