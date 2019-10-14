package nextstep.mvc.tobe.controllermapper;

import nextstep.ConsumerWithException;
import nextstep.mvc.tobe.controllermapper.adepter.ParameterAdapter;
import nextstep.web.annotation.ParameterResolver;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ParameterResolverScanner {
    private static final Logger log = LoggerFactory.getLogger(ParameterResolverScanner.class);
    private final Map<Class<?>, ParameterAdapter> parameterAdapter = new HashMap<>();

    private final Reflections reflections;

    ParameterResolverScanner(Object... basePackage) {
        reflections = new Reflections(basePackage);
        initialize();
    }

    private void initialize() {
        reflections.getTypesAnnotatedWith(ParameterResolver.class).forEach(wrapper(clazz -> parameterAdapter.put(clazz, (ParameterAdapter) clazz.newInstance())));
    }

    private <T extends Class, E extends Exception> Consumer<T> wrapper(ConsumerWithException<T, E> fe) {
        return arg -> {
            try {
                log.debug("parameter resolver clazz : {}", arg);
                fe.apply(arg);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException();
            }
        };
    }

    public List<ParameterAdapter> getParameterAdapter() {
        return new ArrayList<>(parameterAdapter.values());
    }
}
