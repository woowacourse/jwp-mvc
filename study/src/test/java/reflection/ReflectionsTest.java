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
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");

        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);
        logger.debug("Controller annotation classes");
        for (Class<?> aClass : clazz) {
            logger.debug("Controller annotated class : {}", aClass.getName());
        }
        logger.debug("\n");

        clazz = reflections.getTypesAnnotatedWith(Service.class);
        logger.debug("Service annotation classes");
        for (Class<?> aClass : clazz) {
            logger.debug("Service annotated class : {}", aClass.getName());
        }
        logger.debug("\n");

        clazz = reflections.getTypesAnnotatedWith(Repository.class);
        logger.debug("Repository annotation classes");
        for (Class<?> aClass : clazz) {
            logger.debug("Repository annotated class : {}", aClass.getName());
        }
        logger.debug("\n");
    }
}
