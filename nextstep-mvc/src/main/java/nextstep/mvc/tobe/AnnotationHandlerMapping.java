package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize()  {


    }

    private HandlerKey makeHandlerKey(Method method) throws InvocationTargetException, IllegalAccessException {
        Annotation annotation = method.getAnnotation(RequestMapping.class);

        Class<? extends Annotation> type = annotation.annotationType();

        // value, method
        String value = null;
        RequestMethod[] methods = null;
        for (Method annotationMethod : type.getDeclaredMethods()) {
            if ("value".equals(annotationMethod.getName())) {
                value = (String) annotationMethod.invoke(annotation);
            } else if ("method".equals(annotationMethod.getName())) {
                methods = (RequestMethod[]) annotationMethod.invoke(annotation);
            }
        }
        RequestMethod requestMethod = (methods == null) ? null : methods[0];
        return new HandlerKey(value, requestMethod);
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        log.debug("URI: {}", request.getRequestURI());
        HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));

        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }

        HandlerKey emptyMethodHandlerKey = new HandlerKey(request.getRequestURI(), null);
        return handlerExecutions.get(emptyMethodHandlerKey);
    }
}
