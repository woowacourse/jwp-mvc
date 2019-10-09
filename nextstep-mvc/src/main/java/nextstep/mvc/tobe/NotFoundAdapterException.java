package nextstep.mvc.tobe;

import javassist.NotFoundException;

public class NotFoundAdapterException extends NotFoundException {
    public NotFoundAdapterException(String msg) {
        super(msg);
    }
}
