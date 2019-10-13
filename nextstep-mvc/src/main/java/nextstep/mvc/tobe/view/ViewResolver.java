package nextstep.mvc.tobe.view;

public interface ViewResolver {
    boolean support(String viewName);

    View resolve(String viewName);
}
