package nextstep.mvc.tobe.view;

import nextstep.mvc.tobe.ModelAndView;

import java.util.ArrayList;
import java.util.List;

public class ViewResolverManager {
    private static final List<ViewResolver> viewResolvers = new ArrayList<>();

    static {
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new JspViewResolver());
    }

    public static ViewResolver getView(ModelAndView modelAndView) {
        return viewResolvers.stream()
                .filter(viewResolver -> viewResolver.support(modelAndView.getViewName()))
                .findAny()
                .orElse(null);
    }
}
