package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
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
    private static final String TARGET_PACKAGE = "examples";

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections(TARGET_PACKAGE);

        // TODO 클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.
        List<Class<? extends Annotation>> targetAnnotations = Arrays.asList(Controller.class, Service.class, Repository.class);
        for (Class<? extends Annotation> targetAnnotation : targetAnnotations) {
            Set<Class<?>> annotatedClass = reflections.getTypesAnnotatedWith(targetAnnotation);
            logger.debug("annotated {} at {}", targetAnnotation, annotatedClass);
        }
    }
}
