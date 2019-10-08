package slipp.controller;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
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
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class UserApiController {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

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
        resp.setStatus(201);
        resp.setHeader("Location", "/api/users?userId=" + userCreatedDto.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView selectUser(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User savedUser = DataBase.findUserById(userId);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", savedUser);
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();
        UserUpdatedDto userUpdatedDto = JsonUtils.toObject(body, UserUpdatedDto.class);

        User user = DataBase.findUserById(req.getParameter("userId"));
//
//        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
//            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
//        }

        User updateUser = new User(
                req.getParameter("userId"),
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail()
        );

        user.update(updateUser);

        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", updateUser);
        return mav;
    }
}
