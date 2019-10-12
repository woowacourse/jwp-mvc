package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    View resolveViewName(Object view);
}
