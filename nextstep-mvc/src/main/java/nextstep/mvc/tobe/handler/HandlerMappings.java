package nextstep.mvc.tobe.handler;

import nextstep.mvc.exception.HandlerNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

public class HandlerMappings {
    private final List<HandlerMapping> handlerMappings;

    public HandlerMappings(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public void init() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public Object findHandler(HttpServletRequest req) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(HandlerNotFoundException::new);
    }
}
