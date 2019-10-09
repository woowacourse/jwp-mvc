package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private Object[] basePackage;

    private Map<HandlerKey, RequestHandler> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Set<Class<?>> controllerReflections = controllerScanner.collectControllerReflections();
        log.info("Initialized Annotation Request Mapping!");

        for (Class<?> controllerReflection : controllerReflections) {
            Object controller = createController(controllerReflection);

            Stream.of(controllerReflection.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> addHandlerExecution(controller, method));
        }
    }

    private Object createController(Class<?> controllerReflection) {
        try {
            return controllerReflection.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    private void addHandlerExecution(Object controller, Method method) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        RequestMethod[] requestMethods = getRequestMethods(requestMapping);

        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey handlerKey = new HandlerKey(requestMapping.value(), requestMethod);
            HandlerExecution handlerExecution = (req, res) -> (ModelAndView) method.invoke(controller, req, res);

            handlerExecutions.put(handlerKey, new RequestHandler(handlerExecution));
            log.info("path : {}, controller: {}", requestMapping.value(), controller.getClass());
        }
    }

    private RequestMethod[] getRequestMethods(RequestMapping requestMapping) {
        RequestMethod[] requestMethods = requestMapping.method();
        return requestMethods.length == 0 ? RequestMethod.values() : requestMethods;
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
        HandlerKey handlerKey = new HandlerKey(requestURI, requestMethod);
        return handlerExecutions.get(handlerKey);
    }
}
