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
        Set<Class<?>> controllerClazzs = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> serviceClazzs = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositoryClazzs = reflections.getTypesAnnotatedWith(Repository.class);

        for (Class<?> clazz : controllerClazzs) {
            logger.debug(clazz.descriptorString());
        }

        for (Class<?> clazz : serviceClazzs) {
            logger.debug(clazz.descriptorString());
        }

        for (Class<?> clazz : repositoryClazzs) {
            logger.debug(clazz.descriptorString());
        }
    }
}
