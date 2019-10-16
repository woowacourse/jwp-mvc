package slipp.controller;

import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class UserController {
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).filter(user -> user.authenticate(req.getParameter("password")))
        .map(user -> {
            req.getSession().setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return "redirect:/";
        }).orElseGet(() -> {
            req.setAttribute("loginFailed", true);
            return "/user/login.jsp";
        });
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest req, HttpServletResponse res) {
        req.getSession().removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String list(HttpServletRequest req, HttpServletResponse res) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }
        req.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}