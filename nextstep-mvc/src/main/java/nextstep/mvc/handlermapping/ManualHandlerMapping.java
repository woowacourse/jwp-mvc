package nextstep.mvc.handlermapping;

import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMapping.class);

    private final Map<String, Controller> mappings;

    public ManualHandlerMapping(Map<String, Controller> mappings) {
        this.mappings = mappings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Controller> mappings;

        public Builder() {
            this.mappings = new HashMap<>();
        }

        public Builder urlAndController(String url, Controller controller) {
            mappings.put(url, controller);
            return this;
        }

        public ManualHandlerMapping build() {
            return new ManualHandlerMapping(mappings);
        }
    }

    @Override
    public void initialize() {
        logger.info("Initialized Request Mapping!");

        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        return Optional.ofNullable(mappings.get(request.getRequestURI()));
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
