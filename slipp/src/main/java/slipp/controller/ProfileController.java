package slipp.controller;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@nextstep.web.annotation.Controller
public class ProfileController implements HandlerExecution {
    @Override
    @RequestMapping(value = "/users/profile", method = {RequestMethod.GET})
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
        return Optional.ofNullable(request.getParameter("userId"))
                .map(userId -> DataBase.findUserById(userId))
                .map(user -> new ModelAndView(JspView.from("/user/profile.jsp")).addObject("user", user))
                .orElseThrow(() -> new NullPointerException("사용자를 찾을 수 없습니다."));
    }
}
