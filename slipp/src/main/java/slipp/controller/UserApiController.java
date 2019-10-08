package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RequestBodyParser;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> body = RequestBodyParser.parse(request);
        User user = new User(
                body.get("userId"),
                body.get("password"),
                body.get("name"),
                body.get("email"));

        DataBase.addUser(user);

        response.setStatus(SC_CREATED);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        return new ModelAndView(new JsonView())
                .addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> body = RequestBodyParser.parse(request);
        User updatedUser = new User(
                request.getParameter("userId"),
                body.get("password"),
                body.get("name"),
                body.get("email"));

        DataBase.addUser(updatedUser);

        return new ModelAndView(new JsonView())
                .addObject("user", updatedUser);
    }
}
