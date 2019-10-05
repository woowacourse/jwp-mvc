package samples;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.asis.ForwardController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class TestHandlerMapping implements HandlerMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    @Override
    public void initialize() {
        put("/test1", new FirstTestController());
        put("/test2", new SecondTestController());

        mappings.keySet().forEach(path -> {
            logger.info("Path : {}, Controller : {}", path, mappings.get(path).getClass());
        });
    }

    @Override
    public Object getHandler(HttpServletRequest request) {
        return mappings.get(request.getRequestURI());
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

    static class FirstTestController implements Controller {
        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            return "test1";
        }
    }

    static class SecondTestController implements Controller {
        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
            return "test2";
        }
    }
}
