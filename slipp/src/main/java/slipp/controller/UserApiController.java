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
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Controller
public class UserApiController {
    Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserCreatedDto createdDto = JsonUtils.toObject(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())), UserCreatedDto.class);

        DataBase.addUser(createdDto.toUser());

        resp.setStatus(201);
        resp.addHeader("Location", "/api/users?userId=" + createdDto.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");

        resp.setStatus(200);
        return new ModelAndView(new JsonView()).addObject("user", DataBase.findUserById(userId));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(req.getReader().lines().collect(Collectors.joining(System.lineSeparator())), UserUpdatedDto.class);

        User user = DataBase.findUserById(userId);
        user.update(userUpdatedDto.toUser(userId));

        resp.setStatus(200);
        return new ModelAndView(new JsonView()).addObject("user", user);
    }
}