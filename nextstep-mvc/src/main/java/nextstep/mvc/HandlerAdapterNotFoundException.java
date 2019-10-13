package nextstep.mvc;

import nextstep.web.support.HttpStatus;

public class HandlerAdapterNotFoundException extends HttpServletRequestException {
    public HandlerAdapterNotFoundException() {
        super("Handler에 대한 Adapter를 를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
