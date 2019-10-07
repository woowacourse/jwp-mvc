package nextstep.mvc.exception;

import nextstep.mvc.View;

public class ViewRenderException extends RuntimeException {

    private final View view;

    public ViewRenderException(Exception e, View view) {
        super("Error while rendering view: " + view, e);
        this.view = view;
    }
}
