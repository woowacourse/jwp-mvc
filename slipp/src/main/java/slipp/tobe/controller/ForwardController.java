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
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView("home.jsp");
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView userForm(HttpServletRequest req, HttpServletResponse res) {
        return new ModelAndView("/user/form.jsp");
    }
}
