package nextstep.mvc.tobe.support;

import nextstep.utils.BeanUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContext<T> {
    private final Reflections reflections;
    private final Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
    private Object[] basePackage;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContext(Object... basePackage) {
        reflections = new Reflections(basePackage);
        this.basePackage = basePackage;
    }

    public void scanBeans(Class... classes) {
        for (Class clazz : classes) {
            Set<Class<? extends T>> scanClasses = reflections.getSubTypesOf(clazz);
            beans.put(clazz, scanClasses.stream()
                    .map(BeanUtils::createInstance)
                    .collect(Collectors.toSet()));
        }
    }

    public Object getBean(Class<?> clazz) {
        return beans.get(clazz);
    }

    Object[] getBasePackage() {
        return basePackage;
    }

    public Map<Class<?>, Object> getBeans() {
        return beans;
    }

}
