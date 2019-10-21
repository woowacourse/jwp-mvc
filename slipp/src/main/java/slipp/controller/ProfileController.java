package slipp.controller;

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
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private UserService userService = new UserService();

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String show(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        if (userService.isUserExists(userId)) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", userService.findUserById(userId));
        return "/user/profile.jsp";
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public String updateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = userService.findUserById(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        req.setAttribute("user", user);
        return "/user/updateForm.jsp";
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.POST)
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userService.findUserById(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return "redirect:/";
    }
}
