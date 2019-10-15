package slipp.controller;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String USER = "user";
    private static final String USERS = "users";
    private static final String NOT_SAME_USER_ERROR = "다른 사용자의 정보를 수정할 수 없습니다.";
    private static final String NOT_FOUND_USER_ERROR = "사용자를 찾을 수 없습니다.";

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String createUser(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(
                req.getParameter(USER_ID),
                req.getParameter(PASSWORD),
                req.getParameter(NAME),
                req.getParameter(EMAIL)
        );

        log.debug("User : {}", user);

        DataBase.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter(USER_ID));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException(NOT_SAME_USER_ERROR);
        }

        User updateUser = new User(
                req.getParameter(USER_ID),
                req.getParameter(PASSWORD),
                req.getParameter(NAME),
                req.getParameter(EMAIL)
        );
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter(USER_ID);
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException(NOT_FOUND_USER_ERROR);
        }
        req.setAttribute(USER, user);
        return new ModelAndView(new JspView("/user/profile.jsp"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }
        req.setAttribute(USERS, DataBase.findAll());
        return new ModelAndView(new JspView("/user/list.jsp"));
    }
}
