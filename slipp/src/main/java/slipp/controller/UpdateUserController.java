package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static nextstep.web.annotation.RequestMethod.*;

@Controller
public class UpdateUserController {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @RequestMapping(value = "/users/update", method = POST)
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        ModelAndView mav = new ModelAndView();
        User updateUser = new User(req.getParameter("userId"), req.getParameter("email"), req.getParameter("name"),req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);

        mav.addObject("user",user);
        mav.setViewName("redirect:/");
        return mav;
    }
}
