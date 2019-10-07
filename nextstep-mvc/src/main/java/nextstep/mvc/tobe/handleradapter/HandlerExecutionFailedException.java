package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class HandlerExecutionFailedException extends HttpServletRequestException {
    public HandlerExecutionFailedException() {
        super("요청 처리에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
