package slipp.controller;

import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;
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
public class CreateUserController {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private static final String SIGNUP_FORM = "/user/form.jsp";

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String signupForm() {
        return SIGNUP_FORM;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public View signup(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return new RedirectView("/");
    }
}
