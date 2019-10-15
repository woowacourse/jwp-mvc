package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
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
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(req.getReader(), UserCreatedDto.class);

        User user = userCreatedDto.toUser();
        DataBase.addUser(user);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView lookup(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", DataBase.findUserById(userId));
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("userId");
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(req.getReader(), UserUpdatedDto.class);

        User user = DataBase.findUserById(id);
        user.update(userUpdatedDto.toUser(id));

        return new ModelAndView(new JsonView());
    }
}
