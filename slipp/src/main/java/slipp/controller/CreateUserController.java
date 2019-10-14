package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.dto.UserCreatedDto;
import slipp.service.UserService;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.POST;

@Controller
public class CreateUserController {

    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);
    private UserService userService=new UserService();

    @RequestMapping(value = "/users/create", method = POST)
    public ModelAndView userSignUp(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        UserCreatedDto userCreatedDto = new UserCreatedDto(req.getParameter("userId"),
            req.getParameter("password"),
            req.getParameter("name"),
            req.getParameter("email"));

        userService.createUser(userCreatedDto);

        ModelAndView mav = new ModelAndView();

        mav.addObject("user", userCreatedDto);
        mav.setViewName("redirect:/");
        return mav;
    }
}
