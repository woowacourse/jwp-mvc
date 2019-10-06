package slipp;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ControllerAdapter;
import nextstep.mvc.asis.ForwardController;
import nextstep.mvc.tobe.handlermapping.HandlerMapping;
import nextstep.mvc.tobe.handlermapping.annotationmapping.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(ManualHandlerMapping.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

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
