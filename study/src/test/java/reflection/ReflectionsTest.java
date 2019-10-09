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
        // TODO 클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.

        Reflections reflections = new Reflections("examples");

        logAnnotatedTypes(reflections, Controller.class);
        logAnnotatedTypes(reflections, Service.class);
        logAnnotatedTypes(reflections, Repository.class);
    }

    private void logAnnotatedTypes(Reflections reflections, Class annotation) {
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> controller : controllers) {
            logger.debug("{} : {}", annotation.getSimpleName(), controller.getSimpleName());
        }
    }
}
