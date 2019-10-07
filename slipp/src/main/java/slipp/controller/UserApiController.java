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
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserApiController {

    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        User user = parseBody(req, User.class);
        DataBase.addUser(user);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        resp.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(new JsonView()).addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        UserUpdatedDto userUpdatedDto = parseBody(req, UserUpdatedDto.class);
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.update(new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail()));

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.addHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView();
    }

    private <T> T parseBody(HttpServletRequest req, Class<T> clazz) {
        try {
            return JsonUtils.toObject(req.getInputStream(), clazz);
        } catch (IOException e) {
            logger.error("Error on parsing json body", e);
            throw new MessageParseException(e);
        }
    }
}
