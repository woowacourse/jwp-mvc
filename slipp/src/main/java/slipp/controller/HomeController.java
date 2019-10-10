package slipp.controller;


import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class HomeController implements HandlerExecution {
    @Override
    @RequestMapping(value = "/", method = {RequestMethod.GET})
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(JspView.from("home.jsp"))
                .addObject("users", DataBase.findAll());
    }
}
