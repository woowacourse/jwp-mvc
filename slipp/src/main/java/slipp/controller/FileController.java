package slipp.controller;

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
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String getUserForm(HttpServletRequest request, HttpServletResponse response) {
        return "/user/form.jsp";
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public String getLogInForm(HttpServletRequest request, HttpServletResponse response) {
        return "/user/login.jsp";
    }

    @RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
    public String getUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        req.setAttribute("user", user);
        return "/user/updateForm.jsp";
    }
}
