package slipp.controller;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestParam;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.GET;
import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class LoginController {
    @RequestMapping(value = "/users/loginForm", method = GET)
    public JspView read() {
        return new JspView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = POST)
    public Object execute(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpSession session) {
        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            ModelAndView modelAndView = new ModelAndView("/user/login.jsp");
            modelAndView.addObject("loginFailed", true);
            return modelAndView;
        }

        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }
}
