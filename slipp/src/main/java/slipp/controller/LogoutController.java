package slipp.controller;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    @RequestMapping(method = RequestMethod.POST, value = "/users/logout")
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new RedirectView("/"));
    }
}
