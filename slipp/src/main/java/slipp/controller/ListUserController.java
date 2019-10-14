package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class ListUserController {

    @RequestMapping(value = "/users/list", method = GET)
    public ModelAndView findUsers(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ModelAndView mav = new ModelAndView();

        if (!UserSessionUtils.isLogined(req.getSession())) {
            mav.setViewName("redirect:/users/loginForm");
            return mav;
        }

        mav.setViewName("/user/list.jsp");
        mav.addObject("users", DataBase.findAll());
        return mav;
    }
}
