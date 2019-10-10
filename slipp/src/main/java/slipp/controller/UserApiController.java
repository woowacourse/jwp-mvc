package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.BodyUtils;
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
import java.io.IOException;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(BodyUtils.getBody(request), UserCreatedDto.class);
        User user = new User(userCreatedDto);

        DataBase.addUser(user);
        response.setHeader("Location", request.getRequestURI() + "?userId=" + user.getUserId());
        log.debug("Location:{}", request.getRequestURI() + "?userId=" + user.getUserId());
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        log.debug("User:{}", user);
        return new ModelAndView(new JsonView()).addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(BodyUtils.getBody(request), UserUpdatedDto.class);
        User updateUser = new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail());
        User user = DataBase.findUserById(userId);
        user.update(updateUser);
        log.debug("User:{}", user);

        return new ModelAndView(new JsonView());
    }
}
