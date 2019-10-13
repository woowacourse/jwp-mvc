package nextstep.mvc.tobe.view;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class RequestForwardingFailedException extends HttpServletRequestException {
    public RequestForwardingFailedException() {
        super("Forwarding에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
