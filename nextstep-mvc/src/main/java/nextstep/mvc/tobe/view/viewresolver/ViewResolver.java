package nextstep.mvc.tobe.view.viewresolver;

import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.view.ViewType;

public interface ViewResolver {
    boolean support(ViewType viewType);

    View resolve(String viewName);
}
