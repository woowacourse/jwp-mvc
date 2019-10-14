package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.*;

@Controller
public class LogoutController {

    @RequestMapping(value = "/users/logout", method = GET)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mav = new ModelAndView();
        HttpSession session = req.getSession();
        if (session.getAttribute(UserSessionUtils.USER_SESSION_KEY) == null) {
            resp.setStatus(401);
            return mav;
        }
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);

        mav.setViewName("redirect:/");
        return mav;
    }
}
