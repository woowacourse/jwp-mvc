package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    boolean supports(final Object view);

    View resolveView(final Object view);
}
