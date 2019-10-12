package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView(new JspView("home.jsp"));
    }
}
