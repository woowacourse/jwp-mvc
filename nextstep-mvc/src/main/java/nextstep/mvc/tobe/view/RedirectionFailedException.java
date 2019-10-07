package nextstep.mvc.tobe.view;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class RedirectionFailedException extends HttpServletRequestException {
    public RedirectionFailedException() {
        super("Redirection에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
