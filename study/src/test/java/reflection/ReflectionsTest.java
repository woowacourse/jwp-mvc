package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ReflectionsTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");
        Set<Class<?>> clazz = reflections.getTypesAnnotatedWith(Controller.class);

        List<Class<?>> classes = Arrays.asList(Controller.class, Service.class, Repository.class);

        for (Class annotationClass : classes) {
            logger.debug("class: {}", annotationClass);
            logger.debug("annotated class: " + reflections.getTypesAnnotatedWith(annotationClass).toString());
        }
    }
}
