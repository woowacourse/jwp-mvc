package slipp;

import nextstep.ControllerHandlerAdapter;
import nextstep.HandlerAdapter;
import nextstep.mvc.DispatcherServlet;
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
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Controller getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }

    @Override
    public HandlerAdapter getHandlerAdapter() {
        return new ControllerHandlerAdapter();
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
