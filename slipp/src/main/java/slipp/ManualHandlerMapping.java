package slipp;

import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        this.mappings.put("/", new HomeController());
        this.mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        this.mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        this.mappings.put("/users/profile", new ProfileController());
        this.mappings.put("/users/create", new CreateUserController());
        this.mappings.put("/users/updateForm", new UpdateFormUserController());
        this.mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!");
        this.mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, this.mappings.get(path).getClass());
        });
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return this.mappings.get(request.getRequestURI());
    }

    public void put(String url, Controller controller) {
        this.mappings.put(url, controller);
    }
}