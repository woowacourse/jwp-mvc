package nextstep.mvc.exceptions;

public class ViewResolverNotFoundException extends RuntimeException {

    private static final String VIEW_RESOLVER_NOT_FOUND_MESSAGE = "ViewResolver Not Found!";

    public ViewResolverNotFoundException() {
        super(VIEW_RESOLVER_NOT_FOUND_MESSAGE);
    }
}
