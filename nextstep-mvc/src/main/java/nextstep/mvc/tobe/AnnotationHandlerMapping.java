package nextstep.mvc.tobe;

import com.google.common.collect.Maps;
import nextstep.mvc.HandlerMapping;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.reflections.Reflections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AnnotationHandlerMapping implements HandlerMapping {
    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();
    private final List<Object> controllers = new ArrayList<>();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        (new Reflections(this.basePackage)).getTypesAnnotatedWith(Controller.class).forEach(controller -> {
            try {
                final Object instance = controller.getDeclaredConstructor().newInstance();
                this.controllers.add(instance);
                Stream.of(controller.getDeclaredMethods()).filter(x ->
                        x.isAnnotationPresent(RequestMapping.class)
                ).forEach(method -> {
                    final RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    final HandlerExecution handlerExecution = new HandlerExecution() {
                        @Override
                        public ModelAndView handle(
                                final HttpServletRequest request,
                                final HttpServletResponse response
                        ) throws Exception {
                            return (ModelAndView) method.invoke(instance, request, response);
                        }
                    };
                    this.handlerExecutions.put(
                            new HandlerKey(annotation.value(), annotation.method()),
                            handlerExecution
                    );
                });
            } catch (Exception e) {
                System.out.println("ㅇㅇㅋ");
            }
        });
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return this.handlerExecutions.get(
                new HandlerKey(
                        request.getRequestURI(),
                        RequestMethod.valueOf(request.getMethod())
                ));
    }
}