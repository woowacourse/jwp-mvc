package slipp.controller.tobe;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.controller.UserSessionUtils;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UpdateLoginController {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String REDIRECT = "redirect:/";
    private static final String LOGIN_PAGE = "/user/login.jsp";
    private static final String LOGIN_FAILED = "loginFailed";

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView(LOGIN_PAGE));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(USER_ID);
        String password = request.getParameter(PASSWORD);
        User user = DataBase.findUserById(userId);

        if (user != null && user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new JspView(REDIRECT));
        }

        request.setAttribute(LOGIN_FAILED, true);
        return new ModelAndView(new JspView(LOGIN_PAGE));
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new JspView(REDIRECT));
    }
}
