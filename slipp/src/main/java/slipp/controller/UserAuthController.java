package slipp.controller;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.exception.UserNotFoundException;
import slipp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserAuthController {
    private static final Logger log = LoggerFactory.getLogger(UserAuthController.class);
    private UserService userService = new UserService();

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        userService.addUser(UserRequestUtils.getUser(req));
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            User user = userService.loginUser(UserRequestUtils.getUserId(req), UserRequestUtils.getPassword(req));
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new RedirectView("/"));
        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
            req.setAttribute("loginFailed", true);
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new RedirectView("/"));
    }
}
