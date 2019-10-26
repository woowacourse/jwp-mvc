package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@nextstep.web.annotation.Controller
public class ProfileController {
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        checkIfExisting(user);
        return createModelAndView(user);
    }

    private void checkIfExisting(User user) {
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
    }

    private ModelAndView createModelAndView(User user) {
        ModelAndView model = new ModelAndView(new JsonView());
        model.addObject("user", user);
        return model;
    }
}
