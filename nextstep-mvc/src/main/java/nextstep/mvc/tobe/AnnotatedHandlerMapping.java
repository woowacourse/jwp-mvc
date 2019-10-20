package nextstep.mvc.tobe;

import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotatedHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotatedHandlerMapping.class);

    private final Map<Mapping, Handler> handlers;

    public AnnotatedHandlerMapping(Object... basePackage) {
        final Map<Class<?>, Object> controllers = ControllerScanner.run(basePackage);
        controllers.forEach((k, v) -> logger.debug(v.toString()));
        this.handlers = initHandlers(controllers);
        this.handlers.forEach((k, v) -> logger.debug(k.toString() + ": " + v.toString()));
    }

    private Map<Mapping, Handler> initHandlers(Map<Class<?>, Object> controllers) {
        return controllers.keySet().stream().map(Class::getDeclaredMethods)
                                            .flatMap(Stream::of)
                                            .filter(x -> x.isAnnotationPresent(RequestMapping.class))
                                            .map(x -> {
                                                final RequestMapping annotation = x.getAnnotation(RequestMapping.class);
                                                return Map.entry(
                                                        new Mapping(annotation.value(), annotation.method()),
                                                        (Handler) (req, res) ->
                                                            (ModelAndView) x.invoke(
                                                                    controllers.get(x.getDeclaringClass()), req, res
                                                        )
                                                );
                                            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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