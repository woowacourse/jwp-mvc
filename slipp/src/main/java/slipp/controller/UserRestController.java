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

@Controller
public class UserRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = JsonUtils.toObject(req, User.class);
        logger.debug("User : {}", user);

        DataBase.addUser(user);

        resp.setStatus(201);
        resp.setHeader("location", "/api/users?userId=" + user.getUserId());

        ModelAndView mv = new ModelAndView("JsonView");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        ModelAndView mv = new ModelAndView("JsonView");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = DataBase.findUserById(req.getParameter("userId"));
        User updateUser = JsonUtils.toObject(req, User.class);
        logger.debug("Update User : {}", updateUser);
        user.update(updateUser);

        ModelAndView mv = new ModelAndView("JsonView");
        mv.addObject("user", user);
        return mv;
    }
}
