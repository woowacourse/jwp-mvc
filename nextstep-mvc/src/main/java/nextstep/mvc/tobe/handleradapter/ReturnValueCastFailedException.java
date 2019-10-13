package nextstep.mvc.tobe.handleradapter;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class ReturnValueCastFailedException extends HttpServletRequestException {
    public ReturnValueCastFailedException() {
        super("처리할 수 없는 Handler Return Value 입니다.", HttpStatus.NOT_FOUND);
    }
}
