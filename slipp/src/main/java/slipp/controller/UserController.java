package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static nextstep.web.annotation.RequestMethod.GET;
import static nextstep.web.annotation.RequestMethod.POST;
import static nextstep.web.annotation.RequestMethod.PUT;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/api/users", method = GET)
    public ModelAndView read(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        User user = DataBase.findUserById(request.getParameter("userId"));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
            request.getParameter("email"));
        log.debug("User : {}", user);
        DataBase.addUser(user);

        response.setStatus(201);
        response.addHeader("Location", "/api/users?userId=" + user.getUserId());

        log.debug("UserId : {}",user.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user",user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
