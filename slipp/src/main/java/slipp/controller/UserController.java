package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.ModelAttribute;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dto.UserCreatedDto;
import slipp.service.UserService;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    UserService userService = new UserService();

    @RequestMapping(value = "/users/form")
    public String createForm(HttpServletRequest req, HttpServletResponse resp) {
        return "/user/form.jsp";
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String create(@ModelAttribute UserCreatedDto userCreatedDto, HttpServletRequest req, HttpServletResponse resp) {
        userService.create(userCreatedDto);
        return "redirect:/";
    }

    @RequestMapping(value = "/users/list")
    public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new RedirectView("/users/loginForm"));
        }

        req.setAttribute("users", DataBase.findAll());
        return new ModelAndView("/user/list.jsp");
    }
}
