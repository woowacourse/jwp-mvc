package nextstep.mvc.tobe.view.returntyperesolver;

import nextstep.mvc.tobe.ModelAndView;

public interface ViewReturnTypeResolver {
    boolean support(Object handlerResult);

    ModelAndView resolve(Object handlerResult);
}
