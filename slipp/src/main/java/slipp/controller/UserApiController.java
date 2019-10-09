package slipp.controller;

import nextstep.mvc.view.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserApiController {
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = JsonUtils.toObject(req.getInputStream(), User.class);
        DataBase.addUser(user);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest req, HttpServletResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        return new ModelAndView().addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter("userId"));
        User updatedUser = JsonUtils.toObject(req.getInputStream(), User.class);
        user.update(updatedUser);
        return new ModelAndView().addObject("user", user);
    }
}
