package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.*;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(@RequestBody User user, HttpServletResponse response) {
        DataBase.addUser(user);

        response.setStatus(SC_CREATED);
//        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        response.setHeader("Location", "/api/users/" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET)
    public ModelAndView find(@PathVariable String userId, HttpServletRequest request) {
//        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        return new ModelAndView(new JsonView())
                .addObject("user", user);
    }

    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.PUT)
    public ModelAndView update(@PathVariable String userId, @RequestBody User updateUser, HttpServletRequest request) {
//        User user = DataBase.findUserById(request.getParameter("userId"));
        User user = DataBase.findUserById(userId);

        user.update(updateUser);

        return new ModelAndView(new JsonView())
                .addObject("user", user);
    }
}
