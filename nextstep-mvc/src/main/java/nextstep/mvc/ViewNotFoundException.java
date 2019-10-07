package nextstep.mvc;

import nextstep.web.support.HttpStatus;

public class ViewNotFoundException extends HttpServletRequestException {
    public ViewNotFoundException() {
        super("View를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
