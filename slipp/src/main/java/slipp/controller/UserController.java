package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.exception.NotFoundUserException;
import slipp.controller.exception.UnAuthorizedException;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView createUser(UserCreatedDto userCreatedDto) {
        User user = new User(userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail());

        logger.debug("User : {}", user);

        DataBase.addUser(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView showUserList(HttpSession session) {
        if (!UserSessionUtils.isLogined(session)) {
            return new ModelAndView("redirect:/users/loginForm");
        }
        session.setAttribute("users", DataBase.findAll());
        return new ModelAndView("/user/list.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(String userId, String password, HttpSession session) {
        User user = DataBase.findUserById(userId);
        if (user != null && user.matchPassword(password)) {
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView("redirect:/");
        }
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(String userId) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NotFoundUserException(userId);
        }

        ModelAndView modelAndView = new ModelAndView("/user/profile.jsp");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView showUpdateForm(String userId, HttpSession session) {
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(session, user)) {
            throw new UnAuthorizedException(userId);
        }

        ModelAndView modelAndView = new ModelAndView("/user/updateForm.jsp");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(String userId, UserUpdatedDto userUpdatedDto, HttpSession session) {
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(session, user)) {
            throw new UnAuthorizedException(userId);
        }

        User updateUser = new User(userId,
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail());

        logger.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String showUserForm() {
        return "/user/form.jsp";
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public String showLoginForm() {
        return "/user/login.jsp";
    }
}
