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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public String loginForm() throws Exception {
        return "/user/login.jsp";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public Object login(HttpServletRequest req, @RequestParam(name = "userId") String userId) throws Exception {
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);

        if (loginFailed(user, password)) {
            req.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login.jsp"));
        }

        HttpSession session = req.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    private boolean loginFailed(User user, String password) {
        return user == null || !user.matchPassword(password);
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
