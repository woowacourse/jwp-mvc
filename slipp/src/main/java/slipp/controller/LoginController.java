package slipp.controller;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static slipp.controller.UserSessionUtils.USER_SESSION_KEY;

@Controller
public class LoginController {
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        try {
            User user = getValidUser(userId, password);
            HttpSession session = req.getSession();
            session.setAttribute(USER_SESSION_KEY, user);
            return "redirect:/";
        } catch (NullPointerException e) {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }

    private User getValidUser(String userId, String password) {
        return DataBase.findUserById(userId)
                .filter(user -> user.matchPassword(password))
                .orElseThrow(NullPointerException::new);
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public String showLoginForm(HttpServletRequest req, HttpServletResponse resp) {
        return "/user/login.jsp";
    }
}
