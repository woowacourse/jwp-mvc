package nextstep.mvc.handler.handlermapping;

import nextstep.mvc.exception.NotSupportedBasePackageTypeException;
import nextstep.mvc.handler.handlermapping.HandlerExecutionHandlerMapping.Builder;
import nextstep.utils.AnnotatedMethodScanner;
import nextstep.utils.ComponentScanner;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);
    private static final AnnotatedMethodScanner ANNOTATED_METHOD_SCANNER = AnnotatedMethodScanner.getInstance();

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

        List<Method> handlerMethods = findHandlerMethods();
        registerHandlerMethods(handlerMethods, mappingBuilder);

        mapping = mappingBuilder.build();
        mapping.initialize();
    }

    private List<Method> findHandlerMethods() {
        List<Class<?>> controllerClasses = Arrays.asList(basePackages).stream()
                .map(basePackage -> validateBasePackage(basePackage))
                .map(basePackage -> ComponentScanner.fromBasePackagePrefix(basePackage))
                .map(componentScanner -> componentScanner.scan(Controller.class).keySet())
                .flatMap(keySet -> keySet.stream())
                .collect(Collectors.toList());

        return ANNOTATED_METHOD_SCANNER.scan(controllerClasses, RequestMapping.class);
    }

    private String validateBasePackage(Object basePackage) {
        if (!(basePackage instanceof String)) {
            log.error("not supported basePackage: {}", basePackage.getClass());
            throw NotSupportedBasePackageTypeException.basePackage(basePackage);
        }
        return (String) basePackage;
    }

    private void registerHandlerMethods(List<Method> methods, Builder mappingBuilder) {
        for (Method method : methods) {
            registerFromMethod(method, mappingBuilder);
        }
    }

    private void registerFromMethod(Method method, Builder mappingBuilder) {
        HandlerExecution execution = handlerExecutionFactory.fromMethod(method);
        List<HandlerKey> handlerKeys = handlerKeyFactory.fromMethod(method);

        for (HandlerKey key : handlerKeys) {
            mappingBuilder.addKeyAndExecution(key, execution);
        }
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        return mapping.getHandler(request);
    }
}

