package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;
import nextstep.utils.ClassUtils;
import org.reflections.Reflections;

import java.util.List;
import java.util.stream.Collectors;

public class UrlBasedViewResolver implements ViewResolver {
    private List<View> views;

    public UrlBasedViewResolver() {
        views = new Reflections("view")
                .getSubTypesOf(View.class)
                .stream()
                .map(view -> (View) ClassUtils.createInstance(view))
                .collect(Collectors.toList());
    }

    @Override
    public View resolveViewName(Object viesName) {
        if (viesName instanceof View) {
            return (View) viesName;
        }
        return views.stream()
                .filter(view -> view.isMapping(viesName))
                .findAny()
                .orElse(new JsonView());
    }
}
