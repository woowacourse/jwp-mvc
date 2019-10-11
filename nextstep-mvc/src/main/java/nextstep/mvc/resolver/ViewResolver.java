package nextstep.mvc.resolver;

import nextstep.mvc.view.View;

public interface ViewResolver {
    boolean supports(Object view);

    View resolveView(Object view);
}
