package nextstep.mvc.tobe.view;

public class JsonViewResolver implements ViewResolver {
    private static final String JSON_VIEW = "JSON_VIEW";

    @Override
    public boolean support(String viewName) {
        return JSON_VIEW.equals(viewName);
    }

    @Override
    public View resolve(String viewName) {
        return new JsonView();
    }
}
