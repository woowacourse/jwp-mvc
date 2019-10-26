package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.ControllerAdapter;
import nextstep.mvc.tobe.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.LogoutController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/users/logout", new LogoutController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Handler getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        Controller controller = mappings.get(requestUri);
        return ControllerAdapter.of(controller);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
