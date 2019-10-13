package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.modelandview.ModelAndView;
import nextstep.mvc.view.JsonView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = extractRequestBody(request);
        log.debug("User : {}", user);

        DataBase.addUser(user);
        response.setStatus(201);
        response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        Map<String, Object> model = new HashMap<>();

        model.put("user", user);

        return new ModelAndView(model, new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        User updatedUser = extractRequestBody(request);
        user.update(updatedUser);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        return new ModelAndView(model, new JsonView());
    }

    User extractRequestBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            return objectMapper.readValue(request.getInputStream(), User.class);
        }
        return null;
    }
}
