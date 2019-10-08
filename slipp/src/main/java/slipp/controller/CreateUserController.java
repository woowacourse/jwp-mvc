package slipp.controller;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class CreateUserController {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @RequestMapping(value = "/api/users/create", method = POST)
    public ModelAndView userSignUp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
            req.getParameter("email"));
        log.debug("User : {}", user);

        DataBase.addUser(user);
        return new ModelAndView(new JspView("redirect:/"));
    }
}
