package nextstep.mvc.tobe.adapter;

import javassist.NotFoundException;

public class NotFoundAdapterException extends NotFoundException {
    public NotFoundAdapterException(String msg) {
        super(msg);
    }
}
