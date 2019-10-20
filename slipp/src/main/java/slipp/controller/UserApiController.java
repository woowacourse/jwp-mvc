package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserCreatedDto userDto = mapper.readValue(req.getReader(), UserCreatedDto.class);
        User user = new User(userDto.getUserId(), userDto.getPassword(), userDto.getName(), userDto.getEmail());
        DataBase.addUser(user);

        resp.setHeader("Location", "/api/users?userId=" + user.getUserId());
        resp.setStatus(HttpStatus.CREATED.value());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        resp.setStatus(HttpStatus.OK.value());
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserUpdatedDto userUpdatedDto = mapper.readValue(req.getReader(), UserUpdatedDto.class);
        String userId = req.getParameter("userId");

        User updatedUser = new User(userId, userUpdatedDto.getPassword(),
                userUpdatedDto.getName(), userUpdatedDto.getEmail());
        User targetUser = DataBase.findUserById(userId);
        targetUser.update(updatedUser);

        resp.setStatus(HttpStatus.OK.value());
        return new ModelAndView(new JsonView());
    }
}
