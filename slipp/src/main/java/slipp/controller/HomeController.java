package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public ModelAndView indexPage(final HttpServletRequest request, final HttpServletResponse response) {
        request.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("home"));
    }
}
