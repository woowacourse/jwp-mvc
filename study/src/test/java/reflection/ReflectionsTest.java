package reflection;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ReflectionsTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");

        List<Class<? extends Annotation>> annotations = Arrays.asList(annotation.Controller.class, annotation.Repository.class, annotation.Service.class);
        annotations.forEach(annotation -> printComponent(reflections, annotation));
    }

    private void printComponent(Reflections reflections, Class<? extends Annotation> annotation) {
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(annotation);
        components.forEach(controller -> logger.debug("{} : {}", annotation.getName(), controller.getName()));
    }
}
