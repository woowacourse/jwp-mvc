package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    boolean supports(String viewName);

    View resolve(String viewName);
}
