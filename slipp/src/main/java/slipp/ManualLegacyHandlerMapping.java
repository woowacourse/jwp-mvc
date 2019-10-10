package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.LegacyHandlerMapping;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ManualLegacyHandlerMapping implements LegacyHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Controller getHandler(String requestUri) {
        return mappings.get(requestUri);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
