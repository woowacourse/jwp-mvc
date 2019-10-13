package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) {
        return ModelAndView.forward("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        logger.info("{}, {}", userId, password);

        User user = DataBase.findUserById(userId);

        logger.info("user {} login!", user);
        if (user == null || !user.matchPassword(password)) {
            request.setAttribute("loginFailed", true);
            return ModelAndView.forward("/user/login.jsp");
        }

        HttpSession session = request.getSession();
        session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
        return ModelAndView.redirect("/");
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.POST)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return ModelAndView.redirect("/");
    }
}
