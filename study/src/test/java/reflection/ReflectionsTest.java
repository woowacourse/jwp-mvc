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

        logger.debug("================== Controller");
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> aClass : controllerClazz) {
            logger.debug(aClass.toGenericString());
        }

        logger.debug("================== Service");
        Set<Class<?>> serviceClazz = reflections.getTypesAnnotatedWith(Service.class);
        for (Class<?> aClass : serviceClazz) {
            logger.debug(aClass.toGenericString());
        }

        logger.debug("================== Repository");
        Set<Class<?>> repositoryClazz = reflections.getTypesAnnotatedWith(Repository.class);
        for (Class<?> aClass : repositoryClazz) {
            logger.debug(aClass.toGenericString());
        }
    }
}
