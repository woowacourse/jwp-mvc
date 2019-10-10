package nextstep.mvc.argumentresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface ArgumentResolver {
    boolean canResolve(Parameter parameter);
    Object resolve(HttpServletRequest request, HttpServletResponse response, Method method, int index) throws Exception;
}
