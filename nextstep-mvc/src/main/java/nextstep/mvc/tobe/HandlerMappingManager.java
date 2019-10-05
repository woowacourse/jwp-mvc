package nextstep.mvc.tobe;

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

    public Object getHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : handlerMappings) {
            Object handler = handlerMapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }

        throw new NotFoundException();
    }
}
