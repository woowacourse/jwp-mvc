package nextstep.mvc.tobe.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public interface Argument {
    boolean isMapping(Parameter parameter);

    Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter);
}
