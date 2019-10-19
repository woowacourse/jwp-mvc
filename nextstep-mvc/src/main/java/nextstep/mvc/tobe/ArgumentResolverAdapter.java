package nextstep.mvc.tobe;

import java.lang.reflect.Parameter;

public interface ArgumentResolverAdapter {
    boolean match(Parameter parameter);

    Object get(Parameter parameter);
}
