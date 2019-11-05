package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (cannotLogin(user, password)) {
            req.setAttribute("loginFailed", true);
            return new ModelAndView(new RedirectView("/user/login"));
        }

        HttpSession session = req.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return new ModelAndView(new RedirectView("/"));
    }

    private boolean cannotLogin(User user, String password) {
        return user == null || !user.matchPassword(password);
    }
}
