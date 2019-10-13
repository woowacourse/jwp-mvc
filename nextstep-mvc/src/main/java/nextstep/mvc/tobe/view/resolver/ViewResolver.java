package nextstep.mvc.tobe.view.resolver;

import nextstep.mvc.tobe.View;

public interface ViewResolver {
    View resolveViewName(String name);
}
