package nextstep.mvc.tobe.controllermapper.adepter;

import nextstep.mvc.tobe.controllermapper.JavaBeanCreateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JavaBeenParameterAdapter implements ParameterAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter) {
        return true;
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) throws Exception {
        return JavaBeanCreateUtil.getJavaBean(request, methodParameter.getParameterType());
    }
}
