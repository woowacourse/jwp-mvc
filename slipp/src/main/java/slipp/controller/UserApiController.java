package slipp.controller;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class UserApiController {

    private static final String USER_ID = "userId";
    private static final String USER = "user";
    private static final String LOCATION = "Location";
    private static final String NOT_SAME_USER_ERROR = "다른 사용자의 정보를 수정할 수 없습니다.";
    private static final int HTTP_STATUS_CREATED = 201;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView selectUser(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter(USER_ID);
        User savedUser = DataBase.findUserById(userId);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject(USER, savedUser);
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();
        UserCreatedDto userCreatedDto = JsonUtils.toObject(body, UserCreatedDto.class);
        User user = new User(
                userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail()
        );
        DataBase.addUser(user);
        resp.setStatus(HTTP_STATUS_CREATED);
        resp.setHeader(LOCATION, "/api/users?userId=" + userCreatedDto.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(body, UserUpdatedDto.class);

        User user = DataBase.findUserById(req.getParameter(USER_ID));

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException(NOT_SAME_USER_ERROR);
        }

        User updateUser = new User(
                req.getParameter(USER_ID),
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail()
        );

        user.update(updateUser);

        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject(USER, updateUser);
        return mav;
    }
}
