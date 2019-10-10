package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.exception.InstanceCreationFailedException;
import nextstep.utils.LoggingUtils;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ControllerScanner {
    private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    public static List<Object> scanControllers(Object[] basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        List<Object> controllers = new ArrayList<>();

        for (Class clazz : controllerClasses) {
            controllers.add(instanceOf(clazz));
        }
        return controllers;
    }

    private static Object instanceOf(Class controllerClass) {
        try {
            Constructor constructor = controllerClass.getConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException
                | InstantiationException | InvocationTargetException e) {
            LoggingUtils.logStackTrace(logger, e);
            throw new InstanceCreationFailedException();
        }
    }
}
