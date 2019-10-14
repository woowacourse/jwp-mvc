package slipp.controller;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.View;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.support.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserFormController {
    private static final Logger log = LoggerFactory.getLogger(UserFormController.class);
    public static final String USER_ID = "userId";
    public static final String NOT_SAME_USER_ERROR = "다른 사용자의 정보를 수정할 수 없습니다.";

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public View userForm(HttpServletRequest req, HttpServletResponse resp) {
        return new JspView("/user/form.jsp");
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public View loginForm(HttpServletRequest req, HttpServletResponse resp) {
        return new JspView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public View updateForm(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter(USER_ID);
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException(NOT_SAME_USER_ERROR);
        }
        req.setAttribute("user", user);
        return new JspView("/user/updateForm.jsp");
    }
}
