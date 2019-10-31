package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.exception.DuplicatedHandlerMappingException;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize() {
        List<Object> controllers = ControllerScanner.scanControllers(basePackage);

        for (Object controller : controllers) {
            Arrays.stream(controller.getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> createHandlerMapping(controller, method));
        }
    }

    private void createHandlerMapping(Object controller, Method method) {
        List<HandlerKey> handlerKeys = getHandlerKeysOf(method);
        HandlerExecution execution = new HandlerExecution(controller, method);

        for (HandlerKey handlerKey : handlerKeys) {
            checkDuplicate(handlerKey);
            handlerExecutions.put(handlerKey, execution);
        }
    }

    private List<HandlerKey> getHandlerKeysOf(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String uri = annotation.value();
        RequestMethod[] requestMethods = getRequestMethods(annotation);

        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(uri, requestMethod))
                .collect(Collectors.toList());
    }

    private RequestMethod[] getRequestMethods(RequestMapping annotation) {
        RequestMethod[] requestMethods = annotation.method();

        return (requestMethods.length == 0) ? RequestMethod.values()
                : requestMethods;
    }

    private void checkDuplicate(HandlerKey handlerKey) {
        if (handlerExecutions.containsKey(handlerKey)) {
            throw new DuplicatedHandlerMappingException();
        }
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        return handlerExecutions.get(new HandlerKey(uri, method));
    }
}
