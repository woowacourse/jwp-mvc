package slipp.controller;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserRestController {
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserCreatedDto.class);
        DataBase.addUser(userCreatedDto.toUser());

        resp.setStatus(201);
        resp.setHeader("Location", "/api/users?userId=" + userCreatedDto.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("userId");

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", DataBase.findUserById(id));

        resp.setStatus(200);

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserUpdatedDto userCreatedDto = JsonUtils.toObject(IOUtils.toString(req.getReader()), UserUpdatedDto.class);

        User user = DataBase.findUserById(req.getParameter("userId"));
        user.update(userCreatedDto.toUser(user.getUserId()));
        DataBase.addUser(user);

        resp.setStatus(200);

        return new ModelAndView(new JsonView());
    }
}
