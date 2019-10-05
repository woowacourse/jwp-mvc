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
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);
        typesAnnotatedWith.addAll(reflections.getTypesAnnotatedWith(Service.class));
        typesAnnotatedWith.addAll(reflections.getTypesAnnotatedWith(Repository.class));

        for (Class<?> clazz : typesAnnotatedWith) {
            logger.debug("class : {}", clazz.getSimpleName());
        }
    }
}
