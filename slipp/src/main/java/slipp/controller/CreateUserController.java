package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.service.CreateUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CreateUserController {
    private static final CreateUserService createUserService = new CreateUserService();

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView signUpPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response) {
        createUserService.addUser(request);

        return new ModelAndView(new RedirectView("/"));
    }
}
