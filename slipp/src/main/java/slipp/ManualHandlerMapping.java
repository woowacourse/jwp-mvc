package slipp;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handlermapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

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
