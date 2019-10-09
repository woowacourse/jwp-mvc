package nextstep.mvc.tobe.view;

import javassist.NotFoundException;

public class NotFoundViewResolverException extends NotFoundException {
    public NotFoundViewResolverException(String msg) {
        super(msg);
    }
}
