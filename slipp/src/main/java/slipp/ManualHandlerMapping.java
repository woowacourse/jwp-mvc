package slipp;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ControllerAdapter;
import nextstep.mvc.tobe.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        Controller controller = mappings.get(requestUri);

        if (Objects.isNull(controller)) {
            return null;
        }
        return new ControllerAdapter(controller);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
