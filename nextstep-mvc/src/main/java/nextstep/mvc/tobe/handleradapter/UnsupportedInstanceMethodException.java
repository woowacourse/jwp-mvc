package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class UnsupportedInstanceMethodException extends HttpServletRequestException {
    public UnsupportedInstanceMethodException() {
        super("지원하지 않는 instance method 입니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
