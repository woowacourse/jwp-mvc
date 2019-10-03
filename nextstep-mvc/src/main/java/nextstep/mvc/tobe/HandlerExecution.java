package nextstep.mvc.tobe;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    Object instance;
    Method method;

    public HandlerExecution(Class clazz, Method method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.instance = clazz.getConstructor().newInstance();
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // TODO: 2019-10-03 null check
        ModelAndView mv = (ModelAndView) method.invoke(instance,request,response);
        if(mv == null){
            return new ModelAndView(new EmptyView());
        }
        return mv;
    }
}
