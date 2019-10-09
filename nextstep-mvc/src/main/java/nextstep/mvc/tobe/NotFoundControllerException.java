package nextstep.mvc.tobe;

import javassist.NotFoundException;

public class NotFoundControllerException extends NotFoundException {
    public NotFoundControllerException(String msg) {
        super(msg);
    }
}
