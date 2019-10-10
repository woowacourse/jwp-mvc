package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.UpdateFormUserController;
import slipp.controller.UpdateUserController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Manual Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Controller getHandler(HttpServletRequest httpServletRequest) {
        return mappings.get(httpServletRequest.getRequestURI());
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
