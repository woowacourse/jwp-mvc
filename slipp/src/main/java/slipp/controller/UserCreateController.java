package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dto.UserCreatedDto;
import slipp.service.UserCreateService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserCreateController {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateController.class);
    private static final UserCreateService userCreateService = new UserCreateService();

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView signUpPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/form.jsp"));
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView signUp(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        logger.debug("Request User Data : {}, {}, {}, {}", userId, password, name, email);
        UserCreatedDto userCreatedDto = new UserCreatedDto(userId, password, name, email);

        userCreateService.addUser(userCreatedDto);

        return new ModelAndView(new RedirectView("/"));
    }
}
