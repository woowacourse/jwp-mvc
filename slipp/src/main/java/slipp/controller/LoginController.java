package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.RequestParam;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String userId, @RequestParam String password, HttpServletRequest request) {
        ;
        User user = DataBase.findUserById(userId);

        if (user == null || !user.matchPassword(password)) {
            request.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login.jsp"));
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new JspView("redirect:/"));
        }

        throw new IllegalArgumentException("fail to login!");
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new JspView("redirect:/"));
    }
}
