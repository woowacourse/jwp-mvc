package slipp.controller;

import com.google.common.io.CharStreams;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestBody;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(@RequestBody User user, HttpServletResponse response) {
        DataBase.addUser(user);

        response.setStatus(SC_CREATED);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        return new ModelAndView(new JsonView())
                .addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request) throws IOException {
        User user = DataBase.findUserById(request.getParameter("userId"));
        User updateUser = JsonUtils.toObject(
                CharStreams.toString(request.getReader()), User.class);

        user.update(updateUser);

        return new ModelAndView(new JsonView())
                .addObject("user", user);
    }
}
