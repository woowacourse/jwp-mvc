package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ForwardController;
import nextstep.mvc.tobe.handler.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.LoginController;
import slipp.controller.LogoutController;
import slipp.controller.ProfileController;
import slipp.controller.UpdateFormUserController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/updateForm", new UpdateFormUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public boolean support(HttpServletRequest req, HttpServletResponse resp) {
        return mappings.get(req.getRequestURI()) != null;
    }

    @Override
    public Object getHandler(HttpServletRequest req) {
        return mappings.get(req.getRequestURI());
    }

}
