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

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", DataBase.findAll());
        return ModelAndView.forward("home.jsp");
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView createForm(HttpServletRequest request, HttpServletResponse response) {
        return ModelAndView.forward("/user/form.jsp");
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));
        logger.debug("User : {}", user);

        DataBase.addUser(user);
        return ModelAndView.redirect("/");
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return ModelAndView.redirect("/users/loginForm");
        }

        request.setAttribute("users", DataBase.findAll());
        return ModelAndView.forward("/user/list.jsp");
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        request.setAttribute("user", user);

        return ModelAndView.forward("/user/profile.jsp");
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public ModelAndView updateForm(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        request.setAttribute("user", user);

        return ModelAndView.forward("/user/updateForm.jsp");
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        User user = DataBase.findUserById(request.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
                request.getParameter("email"));

        logger.debug("Update User : {}", updateUser);
        user.update(updateUser);

        return ModelAndView.redirect("/");
    }
}
