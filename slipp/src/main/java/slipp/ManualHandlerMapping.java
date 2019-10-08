package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/", new HomeController());
        mappings.put("/users", new ListUserController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    @Override
    public boolean containsKey(HttpServletRequest request) {
        return mappings.containsKey(request.getRequestURI());
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        try {
            Class clazz = mappings.get(request.getRequestURI()).getClass();
            Method method = clazz.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
            return new HandlerExecution(clazz, method);
        } catch (NoSuchMethodException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
}
