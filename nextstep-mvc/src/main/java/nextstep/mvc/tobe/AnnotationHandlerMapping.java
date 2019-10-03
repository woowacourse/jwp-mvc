package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() throws InvocationTargetException, IllegalAccessException {
//        Reflections reflections = new Reflections("tobe");
        Reflections reflections = new Reflections("nextstep.mvc.tobe");
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        log.debug("controller classes: {}",controllerClasses.toString());

        for (Class<?> controllerClass : controllerClasses) {
            for (Method method : controllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    log.debug("method: {}", method);

                    Annotation annotaion = method.getAnnotation(RequestMapping.class);

                    Class<? extends Annotation> type = annotaion.annotationType();

                    // value, method
                    String value = null;
                    RequestMethod[] methods = null;
                    for (Method annotationMethod: type.getDeclaredMethods()) {
                        if ("value".equals(annotationMethod.getName())) {
                            value = (String) annotationMethod.invoke(annotaion);
                        }else if("method".equals(annotationMethod.getName())) {
                            methods = (RequestMethod[]) annotationMethod.invoke(annotaion);
                        }
                    }

                    log.debug("value: {}", value);
                    log.debug("method: {}",Arrays.toString(methods));
                }
            }
        }

    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return null;
    }
}
