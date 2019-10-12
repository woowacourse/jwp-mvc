package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.ModelAttribute;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute User user) {
        log.debug("User : {}", user);
        DataBase.addUser(user);
        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }

        return new ModelAndView(new JspView("/user/list.jsp"))
                .addObject("users", DataBase.findAll());
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView renderUserForm() {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView renderUserLoginForm() {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView renderUserUpdateForm(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        return new ModelAndView(new JspView("/user/updateForm.jsp"))
                .addObject("user", user);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(
                userId,
                Objects.isNull(request.getParameter("password")) ? user.getPassword() : request.getParameter("password"),
                Objects.isNull(request.getParameter("name")) ? user.getName() : request.getParameter("name"),
                Objects.isNull(request.getParameter("email")) ? user.getEmail() : request.getParameter("email")
        );

        log.debug("Update User : {}", updateUser);
        user.update(updateUser);

        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView renderUserProfile(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        return new ModelAndView(new JspView("/user/profile.jsp"))
                .addObject("user", user);
    }
}
