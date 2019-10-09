package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Controller
public class ApiController {
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = extractPostRequestBody(request);
        log.debug("User : {}", user);

//        Map<String, Object> model = new HashMap<>();
//        model.put("user", user);

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

        User updatedUser = extractPostRequestBody(request);
        user.update(updatedUser);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        return new ModelAndView(model, new JsonView());
    }

    User extractPostRequestBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            Scanner s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
            return s.hasNext() ? JsonUtils.toObject(s.next(), User.class) : null;
        }
        return null;
    }
}
