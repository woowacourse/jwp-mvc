package nextstep.mvc.tobe.scanner;

import nextstep.mvc.tobe.exception.NotFoundControllerException;
import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    public static Set<Class<?>> scan(Object[] basePackage) {
        return Arrays.stream(basePackage).map(Reflections::new)
                .collect(Collectors.toList())
                .stream()
                .map(r -> r.getTypesAnnotatedWith(Controller.class))
                .findAny()
                .orElseThrow(NotFoundControllerException::new);
    }
}
