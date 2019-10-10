package nextstep.mvc.handlermapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class InOrderHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(InOrderHandlerMapping.class);

    private final List<HandlerMapping> mappings;

    private InOrderHandlerMapping(List<HandlerMapping> mappings) {
        this.mappings = mappings;
    }

    public static InOrderHandlerMapping from(List<HandlerMapping> mappings) {
        return new InOrderHandlerMapping(mappings);
    }

    @Override
    public void initialize() {
        log.debug("called");

        mappings.stream()
                .forEach(mapping -> mapping.initialize());
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        return mappings.stream()
                .map(mapping -> mapping.getHandler(request))
                .filter(handler -> handler.isPresent())
                .map(handler -> handler.get())
                .findFirst();
    }
}
