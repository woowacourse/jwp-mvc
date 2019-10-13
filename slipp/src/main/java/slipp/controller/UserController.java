package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.exception.LoginUserException;
import slipp.controller.exception.NotFoundUserException;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserCreatedDto userDto = OBJECT_MAPPER.readValue(req.getReader(), UserCreatedDto.class);

        User user = new User(userDto.getUserId(),
                userDto.getPassword(),
                userDto.getName(),
                userDto.getEmail());

        DataBase.addUser(user);

        resp.setHeader("Location", "/api/users?userId=" + user.getUserId());
        resp.setStatus(HttpServletResponse.SC_CREATED);

        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");

        log.debug("user ID: {}", userId);

        User user = DataBase.findUserById(userId)
                .orElseThrow(NotFoundUserException::new);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(HttpServletResponse.SC_OK);

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserCreatedDto userDto = OBJECT_MAPPER.readValue(request.getReader(), UserCreatedDto.class);

        String userId = request.getParameter("userId");

        User updateUser = new User(userId,
                userDto.getPassword(),
                userDto.getName(),
                userDto.getEmail());

        if (!UserSessionUtils.isSameUser(request.getSession(), updateUser)) {
            throw new LoginUserException();
        }

        User user = UserSessionUtils.getUserFromSession(request.getSession());
        user.update(updateUser);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(HttpServletResponse.SC_OK);
        return modelAndView;
    }
}
