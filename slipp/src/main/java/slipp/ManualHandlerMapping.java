package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.asis.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.CreateUserController;
import slipp.controller.ProfileController;
import slipp.controller.UpdateFormUserController;
import slipp.controller.UpdateUserController;

import java.util.HashMap;
import java.util.Map;

class ManualHandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initialize() {
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    Controller getHandler(String requestUri) {
        return mappings.get(requestUri);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
