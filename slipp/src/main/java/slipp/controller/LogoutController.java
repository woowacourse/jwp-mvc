package slipp.controller;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class LogoutController {
    @RequestMapping(value = "/users/logout", method = GET)
    public RedirectView execute(HttpSession session) {
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new RedirectView("/");
    }
}
