package nextstep.mvc.tobe.handler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappingManager {
    private static final List<HandlerMapping> handlerMappings = new ArrayList<>();

    static {
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
    }

    public static Object getHandlerMapping(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
    }
}
