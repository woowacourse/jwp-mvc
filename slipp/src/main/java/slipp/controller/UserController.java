package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @RequestMapping("/users/form")
    public ModelAndView signUpForm(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form"));
    }

    @RequestMapping("/users/loginForm")
    public ModelAndView logInForm(final HttpServletRequest request, final HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login"));
    }

    @RequestMapping("/users")
    public ModelAndView userList(final HttpServletRequest request, final HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }
        request.setAttribute("users", DataBase.findAll());
        return new ModelAndView(new JspView("/user/list"));
    }
}
