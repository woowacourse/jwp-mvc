package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@nextstep.web.annotation.Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/create", method = {RequestMethod.POST})
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return new ModelAndView(RedirectView.from("/"));
    }

    @RequestMapping(value = "/users", method = {RequestMethod.GET})
    public ModelAndView showUsers(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView(RedirectView.from("/users/loginForm"));
        }
        return new ModelAndView(JspView.from("/user/list.jsp"))
                .addObject("users", DataBase.findAll());
    }

    @RequestMapping(value = "/users/profile", method = {RequestMethod.GET})
    public ModelAndView showProfile(HttpServletRequest request, HttpServletResponse response) {
        return Optional.ofNullable(request.getParameter("userId"))
                .map(userId -> DataBase.findUserById(userId))
                .map(user -> new ModelAndView(JspView.from("/user/profile.jsp")).addObject("user", user))
                .orElseThrow(() -> new NullPointerException("사용자를 찾을 수 없습니다."));
    }
}
