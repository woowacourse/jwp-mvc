package nextstep.mvc.tobe.view;

public class JsonViewResolver implements ViewResolver {

    @Override
    public boolean support(ViewType viewType) {
        return ViewType.JSON_VIEW == viewType;
    }

    @Override
    public View resolve(String viewName) {
        return new JsonView();
    }
}
