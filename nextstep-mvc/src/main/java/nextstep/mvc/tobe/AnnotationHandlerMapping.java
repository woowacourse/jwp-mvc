package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.scanner.ControllerScanner;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            handlerExecutions.put(handlerKey, execution);
        }
    }

    private List<HandlerKey> getHandlerKeysOf(Method method) {
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        String uri = annotation.value();
        RequestMethod[] requestMethods = annotation.method();

        Stream<RequestMethod> requestMethodsStream = requestMethods.length == 0
                ? filterConnectableKeys(uri, RequestMethod.values())
                : Arrays.stream(requestMethods);

        // TODO: 2019-10-10  다른 컨트롤러에서 똑같은 메서드를 등록한 경우 예외 처리
        return requestMethodsStream
                .map(requestMethod -> new HandlerKey(uri, requestMethod))
                .collect(Collectors.toList());
    }

    private Stream<RequestMethod> filterConnectableKeys(String uri, RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods)
                .filter(requestMethod -> isConnectable(new HandlerKey(uri, requestMethod)));
    }

    private boolean isConnectable(HandlerKey handlerKey) {
        return !handlerExecutions.containsKey(handlerKey);
    }

    @Override
    public HandlerAdapter getHandler(HttpServletRequest request) {
        String uri = request.getRequestURI();
        RequestMethod method = RequestMethod.valueOf(request.getMethod());

        return handlerExecutions.get(new HandlerKey(uri, method));
    }
}
