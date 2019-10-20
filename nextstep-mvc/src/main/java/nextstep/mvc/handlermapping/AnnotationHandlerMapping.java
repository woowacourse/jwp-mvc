package nextstep.mvc.handlermapping;

import nextstep.mvc.handlermapping.HandlerExecutionHandlerMapping.Builder;
import nextstep.utils.ComponentScanner;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final HandlerExecutionFactory handlerExecutionFactory = HandlerExecutionFactory.getInstance();
    private final HandlerKeyFactory handlerKeyFactory = HandlerKeyFactory.getInstance();

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

            registerHandlerFromClass(controllerClass, mappingBuilder);
        }
    }

    private void registerHandlerFromClass(Class<?> controllerClass, Builder mappingBuilder) {
        List<Method> methods = Arrays.asList(controllerClass.getDeclaredMethods());

        methods.stream()
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .forEach(method -> registerFromMethod(method, mappingBuilder));
    }

    private void registerFromMethod(Method method, Builder mappingBuilder) {
        try {
            tryRegisterFromMethod(method, mappingBuilder);
        } catch (RuntimeException e) {
            log.info("error: {}", e);
        }
    }

    private void tryRegisterFromMethod(Method method, Builder mappingBuilder) {
        HandlerExecution execution = handlerExecutionFactory.fromMethod(method)
                .orElseThrow(() -> new RuntimeException("HandlerExecution 을 만족하지 않는 메소드 시그니처입니다."));

        handlerKeyFactory.fromMethod(method).stream()
                .forEach(key -> {
                    mappingBuilder.addKeyAndExecution(key, execution);
                });
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        return mapping.getHandler(request);
    }
}

