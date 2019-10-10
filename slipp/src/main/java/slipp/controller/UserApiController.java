package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static slipp.support.Utils.BodyParser.parseBody;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView showUser(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        return new ModelAndView(new JsonView()).addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView enrollUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCreatedDto userDto = parseBody(req, UserCreatedDto.class);
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());

        DataBase.addUser(user);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.addHeader("Location", "/api/users?userId=" + user.getUserId());

        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserUpdatedDto userDto = parseBody(req, UserUpdatedDto.class);
        String userId = req.getParameter("userId");
        User updatedUser = new User(userId, userDto.getPassword(), userDto.getName(), userDto.getEmail());
        User user = DataBase.findUserById(userId);
        user.update(updatedUser);

        resp.addHeader("Location", "/api/users?userId=" + user.getUserId());

        return new ModelAndView(new JsonView());
    }

}
