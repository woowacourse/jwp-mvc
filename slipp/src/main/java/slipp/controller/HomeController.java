package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = GET)
    public ModelAndView execute() {
        ModelAndView modelAndView = new ModelAndView("home.jsp");
        modelAndView.addObject("users", DataBase.findAll());
        return modelAndView;
    }
}
