package nextstep.mvc.handlermapping;

import nextstep.mvc.handlermapping.HandlerExecutionHandlerMapping.Builder;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.ComponentScanner;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackages;
    // [TODO] 좀 더 확장성 있게 할 수 있지 않을까?
    private HandlerExecutionHandlerMapping mapping;

    public AnnotationHandlerMapping(Object... basePackages) {
        this.basePackages = basePackages;
    }

    @Override
    public void initialize() {
        log.info("Initialized !");
        Builder mappingBuilder = HandlerExecutionHandlerMapping.builder();
        for (Object basePackage : basePackages) {
            if (!(basePackage instanceof String)) {
                log.error("not supported basePackage: {}", basePackage.getClass());
                return;
            }
            registerHandler((String) basePackage, mappingBuilder);
        }

        mapping = mappingBuilder.build();
        mapping.initialize();
    }

    private void registerHandler(String basePackagePrefix, Builder mappingBuilder) {
        ComponentScanner componentScanner = ComponentScanner.fromBasePackagePrefix(basePackagePrefix);

        Map<Class<?>, Object> controllers = componentScanner.scan(Controller.class);
        for (final Class<?> controllerClass : controllers.keySet()) {
            log.info("controllerClass :{}", controllerClass);
            Object controller = controllers.get(controllerClass);

            registerHandlerFromMethods(controllerClass, controller, mappingBuilder);
        }
    }

    // 키와 값을 만들어서 채우는 역할?
    private void registerHandlerFromMethods(Class<?> controllerClass, Object controller, Builder mappingBuilder) {
        List<Method> methods = Arrays.asList(controllerClass.getDeclaredMethods());

        methods.stream()
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> {
                    HandlerKey handlerKey = makeHandlerKey(method);
                    HandlerExecution handlerExecution = makeHandlerExecution(method, controller);

                    mappingBuilder.addKeyAndExecution(handlerKey, handlerExecution);
                });

    }

    // Handler key generator??
    // 컴파일 애러를 내지않고 만들도록 연습해보기
    private HandlerKey makeHandlerKey(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);

        RequestMethod[] methods = requestMapping.method();
        return HandlerKey.fromUrlAndRequestMethod(requestMapping.value(), (methods == null) ? RequestMethod.ALL : methods[0]);
    }

    // 어떻게 보면... HandlerExecution 을 만들어내는 역할
    private HandlerExecution makeHandlerExecution(Method method, Object controller) {
        return (request, response) -> {
            try {
                return (ModelAndView) method.invoke(controller, request, response);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("e: ", e);
                return null;
            }
        };
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        return mapping.getHandler(request);
    }
}

