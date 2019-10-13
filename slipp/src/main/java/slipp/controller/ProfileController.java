package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestParam;
import slipp.domain.User;
import slipp.support.db.DataBase;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class ProfileController {
    @RequestMapping(value = "/users/profile", method = GET)
    public ModelAndView execute(@RequestParam("userId") String userId) {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        ModelAndView modelAndView = new ModelAndView("/user/profile.jsp");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
