package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ReflectionsTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() {
        Reflections reflections = new Reflections("examples");

        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> serviceClazz = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositoryClazz = reflections.getTypesAnnotatedWith(Repository.class);

        controllerClazz.forEach(controller -> logger.debug("Controller: {}", controller));
        serviceClazz.forEach(service -> logger.debug("Service: {}", service));
        repositoryClazz.forEach(repository -> logger.debug("Repository: {}", repository));

    }
}
