package slipp.annotationcontroller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.UserSessionUtils;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView goIndex(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("home"));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = DataBase.findUserById(userId);
        logger.debug("login - user : {}", user);

        if (user == null) {
            request.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login"));
        }
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new RedirectView("/"));
        }
        request.setAttribute("loginFailed", true);
        return new ModelAndView(new JspView("/user/login"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView goProfile(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        logger.debug("userId : {}, profile : {}", userId, user);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        request.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/profile"));
    }
}
