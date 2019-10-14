package nextstep.mvc.tobe.view;

public enum ViewType {
    JSP_VIEW("JSP VIEW"),
    JSON_VIEW("JSON VIEW");

    private String viewName;

    ViewType(String viewName) {
        this.viewName = viewName;
    }
}
