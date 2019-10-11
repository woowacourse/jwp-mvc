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
import slipp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserService userService = new UserService();

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView showUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userService.findByUserId(UserRequestUtils.getUserId(req));
        checkUser(req, user);

        ModelAndView modelAndView = new ModelAndView(new JspView("/user/updateForm.jsp"));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userService.findByUserId(UserRequestUtils.getUserId(req));
        checkUser(req, user);
        userService.updateUser(UserRequestUtils.getUser(req));
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userService.findByUserId(UserRequestUtils.getUserId(req));

        ModelAndView modelAndView = new ModelAndView(new JspView("/user/profile.jsp"));
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView showUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }

        ModelAndView modelAndView = new ModelAndView(new JspView("/user/list.jsp"));
        modelAndView.addObject("users", userService.findAll());
        return modelAndView;
    }

    private void checkUser(HttpServletRequest req, User user) {
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    }
}
