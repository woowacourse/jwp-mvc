package nextstep.mvc.tobe.view;

public interface ViewResolver {
    boolean support(ViewType viewType);

    View resolve(String viewName);
}
