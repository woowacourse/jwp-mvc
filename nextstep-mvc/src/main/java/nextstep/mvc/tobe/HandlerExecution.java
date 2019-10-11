package nextstep.mvc.tobe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecution.class);

    private Class<?> clazz;
    private Method method;

    public HandlerExecution(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Object view = method.invoke(clazz.newInstance(), request, response);
            if (view instanceof String) {
                return new ModelAndView((String) view);
            }
            if (view instanceof ModelAndView) {
                return (ModelAndView) view;
            }

            // Model Object일 경우
            if (view != null) {
                String viewName = RequestToViewNameTranslator.getViewName(request);
                ModelAndView mav = new ModelAndView(viewName);
                String className = view.getClass().getName();
                String modelName = className.substring(0, 1).toLowerCase() + className.substring(1);
                mav.addObject(modelName, view);
                return mav;
            }

            // void일 경우
            String viewName = RequestToViewNameTranslator.getViewName(request);
            return new ModelAndView(viewName);

        } catch (Exception e) {
            log.debug("Exception : {}", e.getMessage());
            // 500 에러
            throw new IllegalArgumentException("500");
        }
    }
}
