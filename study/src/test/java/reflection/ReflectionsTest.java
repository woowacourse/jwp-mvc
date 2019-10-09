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
    private static final Logger log = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");

        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        log.info("Controllers : {}", controllers);

        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        log.info("Services : {}", services);

        Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);
        log.info("Repositories : {}", repositories);
    }
}
