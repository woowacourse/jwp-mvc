package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestParam;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpSession;

import static nextstep.web.annotation.RequestMethod.GET;

@Controller
public class UpdateFormUserController {

    @RequestMapping(value = "/users/updateForm", method = GET)
    public ModelAndView execute(@RequestParam("userId") String userId, HttpSession session) {
        User user = DataBase.findUserById(userId);
        if (!UserSessionUtils.isSameUser(session, user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        ModelAndView modelAndView = new ModelAndView("/user/updateForm.jsp");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
