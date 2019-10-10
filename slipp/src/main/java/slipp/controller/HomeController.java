package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static nextstep.web.annotation.RequestMethod.GET;
import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = GET)
    public ModelAndView indexPage(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home.jsp");
        mav.addObject("users", DataBase.findAll());
        return mav;
    }

    @RequestMapping(value ="/users/loginForm", method = GET)
    public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("/user/login.jsp");
        return mav;
    }

    @RequestMapping(value="/users/form", method = GET)
    public ModelAndView signUpForm(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/user/form.jsp");
        return mav;
    }
}
