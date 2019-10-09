package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = GET)
    public ModelAndView indexPage(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home.jsp");
        mav.addObject("users", DataBase.findAll());
        return mav;
    }
}
