package slipp.controller.api;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestBody;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestParam;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.*;

@Controller
public class UserApiController {
    @RequestMapping(value = "/api/users", method = POST)
    public ModelAndView create(@RequestBody UserCreatedDto userCreatedDto, HttpServletResponse response) {
        User user = new User(userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail());

        DataBase.addUser(user);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ModelAndView("jsonView");
    }

    @RequestMapping(value = "/api/users", method = GET)
    public ModelAndView read(@RequestParam("userId") String userId) {
        User user = DataBase.findUserById(userId);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = PUT)
    public ModelAndView update(@RequestBody UserUpdatedDto userUpdatedDto, @RequestParam("userId") String userId) {
        User user = new User(userId,
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail());
        DataBase.findUserById(userId).update(user);

        return new ModelAndView("jsonView");
    }
}
