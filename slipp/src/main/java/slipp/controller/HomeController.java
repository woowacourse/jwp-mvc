package slipp.controller;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        return "home.jsp";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("home.jsp"));
    }
}
