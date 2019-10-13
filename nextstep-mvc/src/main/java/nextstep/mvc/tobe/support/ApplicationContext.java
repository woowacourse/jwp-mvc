package nextstep.mvc.tobe.support;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationContext<T> {
    private final Reflections reflections;
    private Logger logger = LoggerFactory.getLogger(ApplicationContext.class);
    private Object[] basePackage;
    private Map<Class<?>, Object> beans = new HashMap<>();

    public ApplicationContext(Object... basePackage) {
        reflections = new Reflections(basePackage);
        this.basePackage = basePackage;
    }

    public void scanBeans(Class... classes) {
        for (Class clazz : classes) {
            Set<Class<? extends T>> scanClasses = reflections.getSubTypesOf(clazz);
            beans.put(clazz, scanClasses.stream().map(x -> createInstance(x)).collect(Collectors.toSet()));
        }
    }

    protected Object createInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.debug("Error: {}", e);
        }
        throw new InstanceCreationFailedException("Error: 인스턴스 생성 실패");
    }

    public Object getBean(Class<?> clazz) {
        return beans.get(clazz);
    }

    public Object[] getBasePackage() {
        return basePackage;
    }

    public Map<Class<?>, Object> getBeans() {
        return beans;
    }

}
