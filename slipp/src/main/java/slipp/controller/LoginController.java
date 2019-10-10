package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class LoginController {

    @RequestMapping(value = "/users/login", method = POST)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mav = new ModelAndView();
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            req.setAttribute("loginFailed", true);
            mav.setViewName("/user/login.jsp");
            return mav;
        }
        if (user.matchPassword(password)) {
            HttpSession session = req.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            mav.setViewName("redirect:/");
            return mav;
        } else {
            req.setAttribute("loginFailed", true);
            mav.setViewName("/user/login.jsp");
            return mav;
        }
    }
}
