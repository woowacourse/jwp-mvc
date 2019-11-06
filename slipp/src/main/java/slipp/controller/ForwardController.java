package slipp.controller;

import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class ForwardController {
    @RequestMapping(value = "/users/form", method = {RequestMethod.GET})
    public ModelAndView handleUserForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(JspView.from("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/loginForm", method = {RequestMethod.GET})
    public ModelAndView handleLoginForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(JspView.from("/user/login.jsp"));
    }
}
