package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;
import nextstep.mvc.tobe.viewresolver.exception.ViewNotFoundException;

import java.util.Set;

public class ViewResolvers {
    private final Set<ViewResolver> viewResolvers;

    public ViewResolvers(Set<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    public View resolve(String viewName) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(viewName))
                .findAny()
                .map(viewResolver -> viewResolver.resolve(viewName))
                .orElseThrow(ViewNotFoundException::new);
    }
}
