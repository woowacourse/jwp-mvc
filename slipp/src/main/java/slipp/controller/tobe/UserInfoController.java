package slipp.controller.tobe;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;
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

@Controller
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
        );

        log.debug("User : {}", user);

        DataBase.addUser(user);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email")
        );
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return new ModelAndView(new JspView("/user/profile.jsp"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest req, HttpServletResponse resp) {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }
        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("/user/list.jsp"));
    }
}
