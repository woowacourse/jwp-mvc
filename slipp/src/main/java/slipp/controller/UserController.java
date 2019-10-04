package slipp.controller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
public class UserController {

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mav = new ModelAndView();

        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        DataBase.addUser(user);

        mav.addObject("user", user);
        mav.setViewName("redirect:/");

        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create2(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mav = new ModelAndView();

        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"), req.getParameter("email"));
        DataBase.addUser(user);

        resp.setStatus(201);
        mav.addObject("user", user);

        return mav;
    }
}


