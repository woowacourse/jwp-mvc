package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.scanner.ControllerScanner;
import nextstep.mvc.scanner.RequestMappingScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
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
//        Reflections reflections = new Reflections("nextstep.mvc.tobe");

        Map<Class<?>, Object> controllers = new ControllerScanner("nextStep.mvc.tobe").getControllers();

        for (Map.Entry<Class<?>, Object> clazz : controllers.entrySet()) {
            Set<Method> methods = RequestMappingScanner.getMethods(clazz.getKey());

            methods.stream().forEach(method -> {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                Arrays.stream(requestMapping.method()).forEach(requestMethod -> {
                    handlerExecutions.put(new HandlerKey(requestMapping.value(), requestMethod), );
                });
            });
        }


//        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        log.debug("controller classes: {}", controllerClasses.toString());

//        for (Class<?> controllerClass : controllerClasses) {
//            for (Method method : controllerClass.getDeclaredMethods()) {
//                if (method.isAnnotationPresent(RequestMapping.class)) {
//                    log.debug("method: {}", method);
//
//                    HandlerKey handlerKey = makeHandlerKey(method);
//
//                    handlerExecutions.put(handlerKey, new HandlerExecution() {
//                        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
//                            return (ModelAndView) method.invoke(controllerClass.getDeclaredConstructor().newInstance(), request, response);
//                        }
//                    });
//                }
//            }
//        }

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
