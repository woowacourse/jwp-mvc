package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@nextstep.web.annotation.Controller
public class LoginController {

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView showLogin(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView("redirect:/");
        } else {
            req.setAttribute("loginFailed", true);
            return new ModelAndView("/user/login.jsp");
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView showLogout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView("redirect:/");
    }
}
