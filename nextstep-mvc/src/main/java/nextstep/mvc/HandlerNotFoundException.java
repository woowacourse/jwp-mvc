package nextstep.mvc;

import nextstep.web.support.HttpStatus;

public class HandlerNotFoundException extends HttpServletRequestException {
    public HandlerNotFoundException() {
        super("요청에 대한 Handler를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
