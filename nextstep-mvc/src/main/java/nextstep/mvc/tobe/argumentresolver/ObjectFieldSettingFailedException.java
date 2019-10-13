package nextstep.mvc.tobe.argumentresolver;

import nextstep.mvc.HttpServletRequestException;
import nextstep.web.support.HttpStatus;

public class ObjectFieldSettingFailedException extends HttpServletRequestException {
    public ObjectFieldSettingFailedException() {
        super("Field Value 주입에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
