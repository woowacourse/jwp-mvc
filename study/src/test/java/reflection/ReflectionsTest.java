package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

public class ReflectionsTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    public void showAnnotationClass() throws Exception {
        Reflections reflections = new Reflections("examples");

        // TODO 클래스 레벨에 @Controller, @Service, @Repository 애노테이션이 설정되어 모든 클래스 찾아 로그로 출력한다.
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);
        Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);

        for (Class<?> controller : controllers) {
            logger.debug("Controller : {}", controller.getName());
            printClassInfo(controller);
        }

        for (Class<?> service : services) {
            logger.debug("Service : {}", service.getName());
            printClassInfo(service);
        }

        for (Class<?> repository : repositories) {
            logger.debug("Repository : {}", repository.getName());
            printClassInfo(repository);
        }
    }

    public void printClassInfo(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Constructor[] constructors = clazz.getDeclaredConstructors();
        Method[] methods = clazz.getDeclaredMethods();

        printConstructsInfo(constructors);
        printFieldsInfo(fields);
        printMethodsInfo(methods);
    }

    private void printConstructsInfo(Constructor[] constructors) {
        for (Constructor constructor : constructors) {
            logger.debug("생성자 이름 : {}, 접근 제어자 : {}, 파라미터 타입 : {}", constructor.getName(), constructor.getModifiers(), constructor.getParameterTypes());
        }
    }

    private void printFieldsInfo(Field[] fields) {
        for (Field field : fields) {
            logger.debug("필드 이름 : {}, 접근 제어자 : {}", field.getName(), field.getModifiers());
        }
    }

    private void printMethodsInfo(Method[] methods) {
        for (Method method : methods) {
            logger.debug("메소드 이름 : {}, 접근 제어자 : {}, 파라미터 타입 : {}",
                    method.getName(),
                    method.getModifiers(),
                    method.getParameterTypes());
        }
    }
}
