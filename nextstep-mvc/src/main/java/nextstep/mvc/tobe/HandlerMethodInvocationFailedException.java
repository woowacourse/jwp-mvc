package nextstep.mvc.tobe;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class HandlerMethodInvocationFailedException extends HttpServletRequestException {
    public HandlerMethodInvocationFailedException() {
        super("Handler Method를 실행할 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
