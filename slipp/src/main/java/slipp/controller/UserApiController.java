package slipp.controller;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.utils.JsonUtils;
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

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(
                req.getInputStream(),
                UserCreatedDto.class
        );

        DataBase.addUser(userCreatedDto.toUser());

        return new ModelAndView(new JsonView()
                .created("/api/users?userId=" + userCreatedDto.getUserId()));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest req, HttpServletResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));

        return new ModelAndView(new JsonView().ok())
                .addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserUpdatedDto userCreatedDto = JsonUtils.toObject(
                req.getInputStream(),
                UserUpdatedDto.class
        );

        User user = DataBase.findUserById(req.getParameter("userId"));
        user.update(userCreatedDto.toUser(user.getUserId()));
        DataBase.addUser(user);

        return new ModelAndView(new JsonView().ok());
    }
}
