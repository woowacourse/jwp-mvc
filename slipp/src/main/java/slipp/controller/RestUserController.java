package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
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
import java.util.stream.Collectors;

@Controller
public class RestUserController {
    private static final Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        logger.debug(System.lineSeparator());
        User user = parseBody(request);
        DataBase.addUser(user);

        response.setStatus(201);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findByUserId(HttpServletRequest request, HttpServletResponse response) {
        final String userId = request.getParameter("userId");
        logger.debug("Request GET /api/users?userId=" + userId);
        User user = DataBase.findUserById(userId);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(200);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        final String userId = request.getParameter("userId");
        logger.debug("Request PUT /api/users?userId=" + userId);

        User user = DataBase.findUserById(userId);
        User updatedUser = parseBody(request);
        user.update(updatedUser);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(200);
        return modelAndView;
    }

    private User parseBody(HttpServletRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String body = request.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            return objectMapper.readValue(body, User.class);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
