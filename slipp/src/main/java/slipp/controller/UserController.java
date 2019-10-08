package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RequestBodyParser;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest req, HttpServletResponse res) {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));

        DataBase.addUser(user);
        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }

        return new ModelAndView(new JspView("/user/list.jsp"))
                .addObject("users", DataBase.findAll());
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView renderUserForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView renderUserLoginForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView renderUserUpdateForm(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        return new ModelAndView(new JspView("/user/updateForm.jsp"))
                .addObject("user", user);
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        Map<String, String> body = RequestBodyParser.parse(request);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updatedUser = new User(
                userId,
                body.get("password"),
                body.get("name"),
                body.get("email"));

        log.debug("Update User : {}", updatedUser);
        user.update(updatedUser);

        return new ModelAndView(new JspView("redirect:/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView renderUserProfile(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        return new ModelAndView(new JspView("/user/profile.jsp"))
                .addObject("user", user);
    }
}
