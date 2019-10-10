package nextstep.mvc.argumentresolver;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ServletArgumentResolver implements ArgumentResolver{
    @Override
    public boolean canResolve(Parameter parameter) {
        Class<?> type = parameter.getType();
        return type.isInstance(HttpServletRequest.class) ||
                type.isInstance(HttpServletResponse.class) ||
                type.isInstance(HttpSession.class) ||
                type.isInstance(ModelAndView.class);
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response,  Method method, int index) throws Exception {
        Class<?> paramType = method.getParameterTypes()[index];
        ServletArgumentConverter converter = ServletArgumentConverter.supports(paramType);
        return converter.convert(request, response);
    }
}
