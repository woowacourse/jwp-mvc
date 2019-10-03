package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        final Reflections reflections = new Reflections(basePackage);


        // controller가 있는 클래스 찾는다
        // RequestMapping이 있는 메소드를 찾는다.
        // map에 등록한다
        // todo 리팩토링
        reflections.getTypesAnnotatedWith(Controller.class).forEach(controller -> {
                    try {
                        final Object instance = controller.getConstructor().newInstance();

                        Stream.of(controller.getDeclaredMethods())
                                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                                .forEach(method -> {
                                    logger.debug("controller: {}, method: {}", controller.getName(), method.getName());
                                    final RequestMapping rm = method.getAnnotation(RequestMapping.class);
                                    if (rm.method().length == 0) {
                                        for (final RequestMethod requestMethod : RequestMethod.values()) {
                                            final HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
                                            final HandlerExecution handlerExecution = (req, resp) -> (ModelAndView) method.invoke(instance, req, resp);
                                            handlerExecutions.put(handlerKey, handlerExecution);
                                        }
                                    } else {
                                        for (final RequestMethod requestMethod : rm.method()) {
                                            final HandlerKey handlerKey = new HandlerKey(rm.value(), requestMethod);
                                            final HandlerExecution handlerExecution = (req, resp) -> (ModelAndView) method.invoke(instance, req, resp);
                                            handlerExecutions.put(handlerKey, handlerExecution);
                                        }
                                    }
                                });
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
