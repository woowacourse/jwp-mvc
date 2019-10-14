package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.ModelAndView;

public class ModelAndViewReturnTypeResolver implements ViewReturnTypeResolver {
    @Override
    public boolean support(Object handlerResult) {
        if (handlerResult == null) {
            return false;
        }
        
        return handlerResult instanceof ModelAndView;
    }

    @Override
    public ModelAndView resolve(Object handlerResult) {
        return (ModelAndView) handlerResult;
    }
}
