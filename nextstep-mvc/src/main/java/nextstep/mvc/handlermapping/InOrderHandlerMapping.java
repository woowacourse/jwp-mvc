package nextstep.mvc.handlermapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class InOrderHandlerMapping implements HandlerMapping {
    private final List<HandlerMapping> mappings;

    private InOrderHandlerMapping(List<HandlerMapping> mappings) {
        this.mappings = mappings;
    }

    public static InOrderHandlerMapping from(List<HandlerMapping> mappings) {
        return new InOrderHandlerMapping(mappings);
    }

    @Override
    public void initialize() {
        mappings.stream()
                .forEach(mapping -> mapping.initialize());
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mappings.stream()
                .filter(mapping -> mapping.getHandler(request) != null)
                .findFirst();
    }
}
