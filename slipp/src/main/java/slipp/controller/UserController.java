package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.controller.exception.UserNotFoundException;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView creationForm(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginForm(HttpServletRequest req, HttpServletResponse resp) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest req, HttpServletResponse resp) {
        User user = getAuthenticatedUser(req);
        req.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/updateForm.jsp"));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        User user = createUserByRequestBody(req);

        DataBase.addUser(user);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }

        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("/user/list.jsp"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        checkExisting(user);
        req.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/profile.jsp"));
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        User user = getAuthenticatedUser(req);
        User updatedUser = createUserByRequestBody(req);

        user.update(updatedUser);
        return new ModelAndView(new RedirectView("redirect:/"));
    }

    private User getAuthenticatedUser(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        return user;
    }

    private User createUserByRequestBody(HttpServletRequest req) {
        return new User(
                req.getParameter("userId"), req.getParameter("password"),
                req.getParameter("name"), req.getParameter("email")
        );
    }

    private void checkExisting(User user) {
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }
}
