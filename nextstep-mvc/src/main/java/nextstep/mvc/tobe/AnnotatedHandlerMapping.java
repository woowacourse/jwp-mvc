package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnnotatedHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotatedHandlerMapping.class);

    private final Map<Class<?>, Object> controllers;
    private final Map<Mapping, Handler> handlers;

    public AnnotatedHandlerMapping(Object... basePackage) {
        this.controllers = initControllers(basePackage);
        this.handlers = initHandlers(this.controllers, basePackage);
        this.controllers.forEach((k, v) -> logger.debug(v.toString()));
        this.handlers.forEach((k, v) -> logger.debug(k.toString() + ": " + v.toString()));
    }

    private Map<Class<?>, Object> initControllers(Object... basePackage) {
        return (new Reflections(basePackage)).getTypesAnnotatedWith(Controller.class).stream().map(x -> {
            try {
                return x.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                return null;
            }
        }).filter(Objects::nonNull)
        .collect(
                Collectors.toMap(Object::getClass, Function.identity())
        );
    }

    private Map<Mapping, Handler> initHandlers(Map<Class<?>, Object> controllers, Object... basePackage) {
        return new HashMap<>() {{
            (new Reflections(
                    basePackage,
                    new MethodAnnotationsScanner())
            ).getMethodsAnnotatedWith(RequestMapping.class).forEach(x -> {
                final RequestMapping annotation = x.getAnnotation(RequestMapping.class);
                put(
                        new Mapping(annotation.value(), annotation.method()),
                        (req, res) -> (ModelAndView) x.invoke(controllers.get(x.getDeclaringClass()), req, res)
                );
            });
        }};
    }

    @Override
    public void initialize() {}

    @Override
    public Handler getHandler(HttpServletRequest req) {
        return this.handlers.get(
                new Mapping(
                        req.getRequestURI(),
                        RequestMethod.valueOf(req.getMethod())
                )
        );
    }
}