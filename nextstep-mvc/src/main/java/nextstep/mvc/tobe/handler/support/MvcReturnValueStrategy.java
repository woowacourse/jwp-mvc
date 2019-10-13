package nextstep.mvc.tobe.handler.support;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.exception.ReturnTypeNotSupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MvcReturnValueStrategy implements ReturnValueStrategy {
    private static final Map<Class<?>, Function<Object, ModelAndView>> strategy = new HashMap<>();

    static {
        strategy.put(ModelAndView.class, (object) -> (ModelAndView) object);
        strategy.put(String.class, (object) -> new ModelAndView((String) object));
    }

    public ModelAndView apply(final Object returnValue) {
        Class<?> returnType = returnValue.getClass();
        Function<Object, ModelAndView> function = strategy.get(returnType);
        if (function == null) {
            throw new ReturnTypeNotSupportedException();
        }
        return function.apply(returnValue);
    }
}
