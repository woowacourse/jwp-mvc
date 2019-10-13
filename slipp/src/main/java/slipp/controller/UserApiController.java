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
import slipp.support.utils.BodyParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = JsonUtils.toObject(BodyParser.getBody(request), User.class);
            logger.debug("signUp User: {} ", user);
            DataBase.addUser(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.addHeader("Location", "/api/users?userId=" + user.getUserId());
        } catch (IOException e) {
            logger.error("Sign Up Fail", e);
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView showUser(String userId, HttpServletRequest request) {
        User user = DataBase.findUserById(userId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = JsonUtils.toObject(BodyParser.getBody(request), User.class);
            logger.debug("Update User: {} ", user);
            DataBase.findUserById(userId).update(user);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            logger.error("Update Fail", e);
            return new ModelAndView("redirect:/");
        }

        return new ModelAndView();
    }
}
