package nextstep.mvc.tobe.viewresolver;

import nextstep.mvc.tobe.view.View;

import java.util.Arrays;
import java.util.List;

public class ViewResolverManager implements ViewResolver {
    private List<ViewResolver> viewResolvers;
    private InternalResourceViewResolver internalResourceViewResolver;

    public ViewResolverManager() {
        this.viewResolvers = Arrays.asList(new JsonViewResolver(), new RedirectViewResolver());
        this.internalResourceViewResolver = new JspViewResolver();
    }

    @Override
    public boolean supports(final Object view) {
        return true;
    }

    @Override
    public View resolveView(final Object view) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(view))
                .findFirst()
                .map(viewResolver -> viewResolver.resolveView(view))
                .orElse(internalResourceViewResolver.resolveView(view));
    }

    public void addViewResolver(final ViewResolver viewResolver) {
        viewResolvers.add(viewResolver);
    }

    public void changeInternalResourceResolver(final InternalResourceViewResolver internalResourceResolver) {
        this.internalResourceViewResolver = internalResourceResolver;
    }
}
