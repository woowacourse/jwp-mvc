package reflection;

import annotation.Controller;
import annotation.Repository;
import annotation.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionsTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionsTest.class);

    @Test
    @DisplayName("@Controller 어노테이션이 붙은 정보를 출력하고 1개인지 확인한다.")
    public void findControllerAnnotation() throws Exception {
        Reflections reflections = new Reflections("examples");
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controller : controllers) {
            logger.debug("Controller : {}", controller.getName());
            printClassInfo(controller);
        }

        assertThat(controllers.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("@Service 어노테이션이 붙은 정보를 출력하고 1개인지 확인한다.")
    public void findServiceAnnotation() throws Exception {
        Reflections reflections = new Reflections("examples");
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(Service.class);

        for (Class<?> service : services) {
            logger.debug("Service : {}", service.getName());
            printClassInfo(service);
        }

        assertThat(services.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("@Repository 어노테이션이 붙은 정보를 출력하고 2개인지 확인한다.")
    public void findRepositoryAnnotation() throws Exception {
        Reflections reflections = new Reflections("examples");
        Set<Class<?>> repositories = reflections.getTypesAnnotatedWith(Repository.class);

        for (Class<?> repository : repositories) {
            logger.debug("Repository : {}", repository.getName());
            printClassInfo(repository);
        }

        assertThat(repositories.size()).isEqualTo(2);
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
