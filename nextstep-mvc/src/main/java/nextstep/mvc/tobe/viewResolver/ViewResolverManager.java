package nextstep.mvc.tobe.viewResolver;

import nextstep.mvc.ViewResolver;
import nextstep.mvc.tobe.view.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class ViewResolverManager {
    private List<ViewResolver> viewResolvers;

    public ViewResolverManager() {
        this.viewResolvers = new ArrayList<>();
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new RedirectViewResolver());
    }

    public void add(ViewResolver viewResolver) {
        viewResolvers.add(viewResolver);
    }

    public ViewResolver findViewResolver(ModelAndView mv) throws NotFoundViewResolverException {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.supports(mv))
                .findFirst()
                .orElseThrow(() -> new NotFoundViewResolverException("해당하는 ViewResolver가 없습니다."));
    }
}
