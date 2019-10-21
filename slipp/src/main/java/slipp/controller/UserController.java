package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private static final ModelAndView REDIRECT_TO_ROOT = new ModelAndView(new RedirectView("/"));

    @RequestMapping("/users")
    public ModelAndView userList(final HttpServletRequest request, final HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }
        request.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("/user/list"));
    }

    @RequestMapping("/users/create")
    public ModelAndView createUser(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = makeUserFromParameter(request);
        DataBase.addUser(user);
        return REDIRECT_TO_ROOT;
    }

    @RequestMapping("/users/form")
    public ModelAndView signUpForm(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form"));
    }

    @RequestMapping("/users/loginForm")
    public ModelAndView logInForm(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login"));
    }

    @RequestMapping("/users/profile")
    public ModelAndView userProfile(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = findUserById(request);
        request.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/profile"));
    }

    @RequestMapping("/users/updateForm")
    public ModelAndView updateForm(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = findUserById(request);
        checkIdForUpdate(request, user);
        request.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/updateForm"));
    }

    @RequestMapping("/users/update")
    public ModelAndView update(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = findUserById(request);
        checkIdForUpdate(request, user);
        final User newUser = makeUserFromParameter(request);
        user.update(newUser);
        DataBase.addUser(user);
        return REDIRECT_TO_ROOT;
    }

    private User findUserById(final HttpServletRequest request) {
        return DataBase.findUserById(request.getParameter("userId"));
    }

    private void checkIdForUpdate(final HttpServletRequest request, final User user) {
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
    }

    private User makeUserFromParameter(final HttpServletRequest request) {
        return new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
    }
}
