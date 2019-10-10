package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    View resolveViewName(String viewName);
}
