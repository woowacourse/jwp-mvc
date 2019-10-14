package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.ViewType;

public class JsonViewReturnTypeResolver implements ViewReturnTypeResolver {
    @Override
    public boolean support(Object handlerResult) {
        return !(handlerResult instanceof String || handlerResult instanceof ModelAndView) ;
    }

    @Override
    public ModelAndView resolve(Object handlerResult) {
        ModelAndView modelAndView = new ModelAndView(ViewType.JSON_VIEW);
        String attributeName = handlerResult.getClass().getName();

        return modelAndView.addObject(attributeName, handlerResult);
    }
}
