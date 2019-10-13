package nextstep.mvc.tobe.handler;

import javassist.NotFoundException;
import nextstep.mvc.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappingManager {
    private List<HandlerMapping> handlerMappings;

    public HandlerMappingManager() {
        this.handlerMappings = new ArrayList<>();
    }

    public void initialize() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public void add(HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public Object findHandler(HttpServletRequest req) throws NotFoundException {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new NotFoundHandlerException("해당하는 Handler를 찾을 수 없습니다."));
    }
}
