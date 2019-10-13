package nextstep.mvc.tobe.handler;

import javassist.NotFoundException;

public class NotFoundHandlerException extends NotFoundException {
    public NotFoundHandlerException(String msg) {
        super(msg);
    }
}
