package slipp.controller;

import nextstep.mvc.ModelAndView;
import nextstep.mvc.view.JsonView;
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

import static com.google.common.net.HttpHeaders.LOCATION;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private static final String USER_ID = "userId";
    private static final String USER_ATTRIBUTE_NAME = "user";
    private static final String USER_CREATED_LOCATION = "/api/users?userId=%s";

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter(USER_ID));

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        return modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = createUser(req);

        resp.setStatus(SC_CREATED);
        resp.setHeader(LOCATION, String.format(USER_CREATED_LOCATION, user.getUserId()));

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
        return modelAndView;
    }

    private User createUser(HttpServletRequest req) throws IOException {
        String body = req.getReader().readLine();
        UserCreatedDto createdUser = JsonUtils.toObject(body, UserCreatedDto.class);
        User user = new User(
                createdUser.getUserId(),
                createdUser.getPassword(),
                createdUser.getName(),
                createdUser.getEmail()
        );
        log.debug("User : {}", user);
        DataBase.addUser(user);
        return user;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = updateUser(req);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject(USER_ATTRIBUTE_NAME, user);
        return modelAndView;
    }

    private User updateUser(HttpServletRequest req) throws IOException {
        User user = DataBase.findUserById(req.getParameter(USER_ID));

        String body = req.getReader().readLine();
        UserUpdatedDto updateUserDto = JsonUtils.toObject(body, UserUpdatedDto.class);
        User updateUser = new User(
                user.getUserId(),
                updateUserDto.getPassword(),
                updateUserDto.getName(),
                updateUserDto.getEmail()
        );
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return user;
    }
}
