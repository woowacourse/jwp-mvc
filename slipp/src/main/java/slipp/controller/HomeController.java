package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class HomeController {
    @RequestMapping("/")
    public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("home.jsp"));
    }
}
