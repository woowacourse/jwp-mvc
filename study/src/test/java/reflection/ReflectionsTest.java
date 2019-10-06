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
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");
        Set<Class<?>> controllerClazz = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> serviceClazz = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositoryClazz = reflections.getTypesAnnotatedWith(Repository.class);

        controllerClazz.forEach(value -> LOGGER.debug("class controller: {}", value));
        serviceClazz.forEach(value -> LOGGER.debug("class service: {}", value));
        repositoryClazz.forEach(value -> LOGGER.debug("class repository: {}", value));

//        LOGGER.debug();

        // TODO 클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.
    }
}
