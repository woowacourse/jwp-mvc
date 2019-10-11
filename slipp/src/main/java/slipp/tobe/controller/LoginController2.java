package slipp.tobe.controller;

import nextstep.mvc.tobe.JSPView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.asis.controller.UserSessionUtils;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController2 {

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginForm(HttpServletRequest req, HttpServletResponse rep) {
        return new ModelAndView(new JSPView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse rep) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            req.setAttribute("loginFailed", true);
            return new ModelAndView(new JSPView("/user/login.jsp"));
        }

        return view(req, user, password);
    }

    private ModelAndView view(HttpServletRequest req, User user, String password) {
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new JSPView("redirect:/"));
        }

        req.setAttribute("loginFailed", true);
        return new ModelAndView(new JSPView("/user/login.jsp"));
    }
}
