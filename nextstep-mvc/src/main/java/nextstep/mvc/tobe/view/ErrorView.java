package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.RequestContext;

import java.util.Map;

public class ErrorView implements View {
    private static final String DEFAULT_ERROR_VIEW_NAME = "error.jsp";
    private final String viewName;

    public ErrorView(String viewName) {
        this.viewName = viewName;
    }

    public static View defaultErrorView() {
        return new ErrorView(DEFAULT_ERROR_VIEW_NAME);
    }

    @Override
    public void render(Map<String, ?> model, RequestContext requestContext) {

    }
}
