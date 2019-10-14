package nextstep.mvc.tobe.view.returntyperesolver;

import java.util.ArrayList;
import java.util.List;

public class ViewReturnTypeResolverManager {
    private static final List<ViewReturnTypeResolver> viewReturnTypeResolvers = new ArrayList<>();

    static {
        viewReturnTypeResolvers.add(new ModelAndViewReturnTypeResolver());
        viewReturnTypeResolvers.add(new JspViewReturnTypeResolver());
        viewReturnTypeResolvers.add(new JsonViewReturnTypeResolver());
    }

    public static ViewReturnTypeResolver getViewReturnTypeResolver(Object handlerResult) {
        return viewReturnTypeResolvers.stream()
                .filter(viewReturnTypeResolver -> viewReturnTypeResolver.support(handlerResult))
                .findAny()
                .orElse(null);
    }
}
