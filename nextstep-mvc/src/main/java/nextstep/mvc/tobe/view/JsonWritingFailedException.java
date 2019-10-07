package nextstep.mvc.tobe.view;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class JsonWritingFailedException extends HttpServletRequestException {
    public JsonWritingFailedException() {
        super("Json 변환에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
