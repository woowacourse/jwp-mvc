package slipp.controller;

import nextstep.mvc.view.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class UserController {
    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView showSignupForm(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView signup(HttpServletRequest req, HttpServletResponse res) {
        DataBase.addUser(
                new User(
                        req.getParameter("userId"),
                        req.getParameter("password"),
                        req.getParameter("name"),
                        req.getParameter("email")
                )
        );
        return new ModelAndView(new RedirectView("redirect:/"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView showLoginForm(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).filter(user -> user.authenticate(req.getParameter("password")))
        .map(user -> {
            req.getSession().setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new RedirectView("redirect:/"));
        }).orElseGet(() -> {
            req.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login.jsp"));
        });
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {
        req.getSession().removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new RedirectView("redirect:/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView showProfile(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).map(user -> {
            req.setAttribute("user", user);
            return new ModelAndView(new JspView("/user/profile.jsp"));
        }).orElseThrow(() -> new NullPointerException("사용자를 찾을 수 없습니다."));
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView showUpdateForm(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).filter(user -> UserSessionUtils.isSameUser(req.getSession(), user))
        .map(user -> {
            req.setAttribute("user", user);
            return new ModelAndView(new JspView("/user/updateForm.jsp"));
        }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public String update(HttpServletRequest req, HttpServletResponse res) {
        return Optional.ofNullable(
                DataBase.findUserById(req.getParameter("userId"))
        ).filter(user -> UserSessionUtils.isSameUser(req.getSession(), user))
        .map(user -> {
            final User updateUser = new User(
                    req.getParameter("userId"),
                    req.getParameter("password"),
                    req.getParameter("name"),
                    req.getParameter("email")
            );
            user.update(updateUser);
            return "redirect:/";
        }).orElseThrow(() -> new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다."));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest req, HttpServletResponse res) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("redirect:/users/loginForm"));
        }
        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("/user/list.jsp"));
    }
}