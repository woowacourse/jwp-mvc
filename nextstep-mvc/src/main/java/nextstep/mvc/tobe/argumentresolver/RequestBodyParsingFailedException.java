package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class RequestBodyParsingFailedException extends HttpServletRequestException {
    public RequestBodyParsingFailedException() {
        super("Request Body Parsing에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
