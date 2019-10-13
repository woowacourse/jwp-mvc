package nextstep.mvc.tobe;

import nextstep.mvc.Handler;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class HandlerMappingManager {
    private final List<HandlerMapping> handlerMappings;

    public HandlerMappingManager(List<HandlerMapping> handlerMappings) {
        this.handlerMappings = handlerMappings;
    }

    public void initialize() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public Handler getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                .filter(handlerMapping -> handlerMapping.support(request))
                .findFirst()
                .orElseThrow(NotFoundException::new)
                .getHandler(request);
    }
}
