package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            request.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView("redirect:/");
        } else {
            request.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
            request.getParameter("email"));
        logger.debug("User : {}", user);

        DataBase.addUser(user);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView forwardUserForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/user/form.jsp");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView retriveUsers(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView("redirect:/users/loginForm");
        }

        request.setAttribute("users", DataBase.findAll());
        return new ModelAndView("/user/list.jsp");
    }
}
