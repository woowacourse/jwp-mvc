package slipp.controller;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserRequestLoginDto;
import slipp.service.LoginService;
import slipp.service.UserSearchService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    private static final String USER_ID = "userId";
    private static final String PASSWORD = "password";
    private static final String LOGIN_FAILED = "loginFailed";

    private static final LoginService loginService = new LoginService();
    private static final UserSearchService userSearchService = new UserSearchService();

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        UserRequestLoginDto loginDto = new UserRequestLoginDto(request.getParameter(USER_ID), request.getParameter(PASSWORD));

        if (loginService.matchLoginData(loginDto)) {
            HttpSession session = request.getSession();
            UserCreatedDto userCreatedDto = userSearchService.findUserById(loginDto.getUserId());

            session.setAttribute(UserSessionUtils.USER_SESSION_KEY, userCreatedDto);
            return new ModelAndView(new RedirectView("/"));
        }

        request.setAttribute(LOGIN_FAILED, true);
        return new ModelAndView(new JspView("/user/login.jsp"));
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new RedirectView("/"));
    }
}
