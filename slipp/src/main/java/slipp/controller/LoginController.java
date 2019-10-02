package slipp.controller;

import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@nextstep.web.annotation.Controller
public class LoginController {
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        } else {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        }
    }
}
