package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RequestBodyParser;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> body = RequestBodyParser.parse(request);
        String userId = body.get("userId");
        String password = body.get("password");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            request.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login.jsp"));
        }
        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
            return new ModelAndView(new JspView("redirect:/"));
        } else {
            request.setAttribute("loginFailed", true);
            return new ModelAndView(new JspView("/user/login.jsp"));
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new JspView("redirect:/"));
    }
}
