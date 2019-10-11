package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
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
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Controller
public class ApiUserController {
    private static final Logger log = LoggerFactory.getLogger(ApiUserController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        User user = JsonUtils.toObject(body, User.class);
        DataBase.addUser(user);

        log.debug("location: {}", "/api/users?userId=" + user.getUserId());
        resp.setHeader("Location", "/api/users?userId=" + user.getUserId());
        resp.setStatus(SC_CREATED);

        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest req, HttpServletResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));

        resp.setStatus(SC_OK);
        return new ModelAndView(new JsonView()).addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        User updateUser = JsonUtils.toObject(body, User.class);
        user.update(updateUser);

        resp.setStatus(SC_OK);

        return new ModelAndView(new JsonView());
    }
}
