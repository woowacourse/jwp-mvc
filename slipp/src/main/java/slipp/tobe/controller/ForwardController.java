package slipp.tobe.controller;

import nextstep.mvc.tobe.JSPView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ForwardController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("users", DataBase.findAll());

        return "home.jsp";
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public String userForm(HttpServletRequest req, HttpServletResponse res) {

        return "/user/form.jsp";
    }
}
