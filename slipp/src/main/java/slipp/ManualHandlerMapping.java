package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        logger.info("Initialized Request Mapping!");
        this.mappings.keySet().forEach(path ->
            logger.info("Path : {}, Controller : {}", path, this.mappings.get(path).getClass())
        );
    }

    @Override
    public Controller getHandler(HttpServletRequest req) {
        return this.mappings.get(req.getRequestURI());
    }

    public void put(String url, Controller controller) {
        this.mappings.put(url, controller);
    }
}