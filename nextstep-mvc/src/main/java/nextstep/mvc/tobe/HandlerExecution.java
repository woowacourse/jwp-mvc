package nextstep.mvc.tobe;

import nextstep.mvc.tobe.controllermapper.ControllerParameterMapper;
import nextstep.mvc.tobe.controllermapper.ParameterAdeptersFactory;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);
    private Method method;
    private Object declaredObject;

    public HandlerExecution(Method method, Object declaredObject) {
        this.method = method;
        this.declaredObject = declaredObject;

    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ControllerParameterMapper controllerParameterMapper = new ControllerParameterMapper(method, ParameterAdeptersFactory.getAdepters());
        Object[] objects = controllerParameterMapper.getObjects(request, response);
        log.debug("object : {}", objects);
        return (ModelAndView) method.invoke(declaredObject, objects);
    }

}
