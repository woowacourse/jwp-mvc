package nextstep.mvc.handlermapping;

import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandlerExecutionHandlerMapping implements HandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(HandlerExecutionHandlerMapping.class);

    private final Map<HandlerKey, HandlerExecution> mappings;

    private HandlerExecutionHandlerMapping(Map<HandlerKey, HandlerExecution> mappings) {
        this.mappings = mappings;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<HandlerKey, HandlerExecution> mappings;

        private Builder() {
            this.mappings = new HashMap<>();
        }

        public Builder addKeyAndExecution(HandlerKey handlerKey, HandlerExecution handlerExecution) {
            mappings.put(handlerKey, handlerExecution);

            return this;
        }

        public HandlerExecutionHandlerMapping build() {
            return new HandlerExecutionHandlerMapping(mappings);
        }
    }

    @Override
    public void initialize() {
        log.info("Start initialize..!!");

        log.info("already mapped in constructor");
        log.info("mappings: {}", mappings);
    }

    @Override
    public Optional<Object> getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        log.debug("URI: {}, method: {}", url, requestMethod);

        HandlerKey handlerKey = HandlerKey.fromUrlAndRequestMethod(url, requestMethod);
        if (mappings.containsKey(handlerKey)) {
            return Optional.of(mappings.get(handlerKey));
        }

        HandlerKey keyOfAll = HandlerKey.fromUrlAndRequestMethod(url, RequestMethod.ALL);
        if (mappings.containsKey(keyOfAll)) {
            return Optional.of(mappings.get(keyOfAll));
        }

        return Optional.empty();
    }
}
