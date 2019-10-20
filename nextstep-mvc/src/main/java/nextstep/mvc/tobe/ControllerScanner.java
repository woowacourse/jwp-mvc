package nextstep.mvc.tobe;

import nextstep.web.annotation.Controller;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ControllerScanner {
    public static Map<Class<?>, Object> run(Object... basePackage) {
        return (new Reflections(basePackage)).getTypesAnnotatedWith(Controller.class).stream().map(x -> {
            try {
                return x.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull)
        .collect(Collectors.toMap(Object::getClass, Function.identity()));
    }
}