package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ForwardController;
import nextstep.mvc.tobe.HandlerExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.CreateUserController;
import slipp.controller.LogoutController;
import slipp.controller.UpdateFormUserController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ManualHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public boolean canHandle(HttpServletRequest request) {
        return mappings.containsKey(request.getRequestURI());
    }

    @Override
    public void initialize() {
//        어노테이션 기반 MVC 사용
//        mappings.put("/", new HomeController());
//        mappings.put("/users", new ListUserController());
//        mappings.put("/users/login", new LoginController());
//        mappings.put("/users/profile", new ProfileController());
//        mappings.put("/users/update", new UpdateUserController());

        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());

        logger.info("Initialized Request Mapping!");
        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        return (req, res) -> mappings.get(request.getRequestURI()).execute(req, res);
    }
}
