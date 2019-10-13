package nextstep.mvc.tobe.viewResolver;

import javassist.NotFoundException;

public class NotFoundViewResolverException extends NotFoundException {
    public NotFoundViewResolverException(String msg) {
        super(msg);
    }
}
