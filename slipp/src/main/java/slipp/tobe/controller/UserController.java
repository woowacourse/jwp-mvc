package slipp.tobe.controller;

import nextstep.mvc.tobe.JSPView;
import nextstep.mvc.tobe.ModelAndView;
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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView get(HttpServletRequest req, HttpServletResponse res) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);
        return new ModelAndView(new JSPView("/user/profile.jsp"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView post(HttpServletRequest req, HttpServletResponse res) {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
            req.getParameter("email"));
        logger.debug("User : {}", user);
        DataBase.addUser(user);
        return new ModelAndView(new JSPView("redirect:/"));
    }
}
