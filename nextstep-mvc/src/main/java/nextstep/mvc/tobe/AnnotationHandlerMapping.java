package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import nextstep.mvc.tobe.exception.NotFoundHandlerException;
import nextstep.utils.LoggingUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        List<Object> controllers = getControllers();

        for (Object controller : controllers) {
            Arrays.stream(controller.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> createHandlerMapping(controller, method));
        }
    }

    private List<Object> getControllers() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        List<Object> controllers = new ArrayList<>();

        for (Class controllerClass : controllerClasses) {
            controllers.add(instanceOf(controllerClass));
        }
        return controllers;
    }

    private Object instanceOf(Class controllerClass) {
        try {
            Constructor constructor = controllerClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            LoggingUtils.logStackTrace(logger, e);
            throw new InstanceCreationFailedException();
        }
    }

    private void createHandlerMapping(Object controller, Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        HandlerExecution execution = new HandlerExecution(controller, method);
        RequestMethod[] requestMethods = annotation.method();

        if (requestMethods.length == 0) {
            connectHandlerToAllConnectableRequestMethods(annotation, execution);
            return;
        }

        connectHandler(annotation, execution);
    }

    private void connectHandlerToAllConnectableRequestMethods(RequestMapping annotation,
                                                              HandlerExecution execution) {
        String uri = annotation.value();
        List<RequestMethod> connectable = Arrays.stream(RequestMethod.values())
                .filter(requestMethod -> isConnectable(new HandlerKey(uri, requestMethod)))
                .collect(Collectors.toList());

        HandlerKey handlerKey;
        for (RequestMethod requestMethod : connectable) {
            handlerKey = new HandlerKey(uri, requestMethod);
            handlerExecutions.put(handlerKey, execution);
        }
    }

    private boolean isConnectable(HandlerKey handlerKey) {
        return !handlerExecutions.containsKey(handlerKey);
    }

    private void connectHandler(RequestMapping annotation, HandlerExecution execution) {
        String uri = annotation.value();

        for (RequestMethod requestMethod : annotation.method()) {
            HandlerKey handlerKey = new HandlerKey(uri, requestMethod);
            handlerExecutions.put(handlerKey, execution);
        }
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());
        HandlerExecution handlerExecution = handlerExecutions.get(new HandlerKey(uri, method));

        checkNull(handlerExecution);
        return handlerExecution;
    }

    private void checkNull(HandlerExecution handlerExecution) {
        if (handlerExecution == null) {
            throw new NotFoundHandlerException();
        }
    }
}
