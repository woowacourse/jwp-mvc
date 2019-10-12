package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
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
import java.util.stream.Collectors;

@Controller
public class UserApiController {
    public static final String USER_ID = "userId";
    public static final String USER = "user";
    public static final String LOCATION = "location";
    public static final String LOCATION_PREFIX = "/api/users?userId=";
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(USER_ID);
        User user = DataBase.findUserById(userId);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject(USER, user);

        return modelAndView;

    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter(USER_ID);
        User foundUser = DataBase.findUserById(userId);
        log.debug("user update");
        log.debug("userId: {}, found: {}", userId, foundUser);

        String userData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(userData, UserUpdatedDto.class);
        User updatedUser = new User(foundUser.getUserId(), userUpdatedDto.getPassword(),
                userUpdatedDto.getName(), userUpdatedDto.getEmail());

        DataBase.replace(updatedUser);

        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userData = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        UserCreatedDto userCreatedDto = JsonUtils.toObject(userData, UserCreatedDto.class);
        User user = userCreatedDto.toUser();
        DataBase.addUser(user);

        response.addHeader(LOCATION, LOCATION_PREFIX + user.getUserId());
        response.setStatus(201);
        return new ModelAndView(new JsonView());
    }
}
