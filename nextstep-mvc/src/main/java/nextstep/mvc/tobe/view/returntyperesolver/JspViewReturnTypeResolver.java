package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.ViewType;

public class JspViewReturnTypeResolver implements ViewReturnTypeResolver {
    @Override
    public boolean support(Object handlerResult) {
        if (handlerResult == null) {
            return false;
        }

        return handlerResult instanceof String;
    }

    @Override
    public ModelAndView resolve(Object handlerResult) {
        return new ModelAndView(ViewType.JSP_VIEW, (String) handlerResult);
    }
}
