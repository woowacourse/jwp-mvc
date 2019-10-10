package slipp.controller;

import nextstep.mvc.view.JspView;
import nextstep.mvc.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class FormController {

    @RequestMapping(method = RequestMethod.GET, value = "/users/form")
    public ModelAndView showUserForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/loginForm")
    public ModelAndView showLoginForm(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }
}
