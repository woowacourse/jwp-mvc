package slipp.controller;

import com.google.common.io.CharStreams;
import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ModelAndView;
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
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Controller
public class UserApiController {

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = JsonUtils.toObject(request.getReader(), User.class);
        DataBase.addUser(user);
        response.setStatus(SC_CREATED);
        response.setHeader("Location", "/api/users?userId=pobi");
        return new ModelAndView(new JspView("/home.jsp"));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView show(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        String userId = request.getParameter("userId");
        if (userId == null) {
            modelAndView.addObject("user", DataBase.findAll());
        } else {
            modelAndView.addObject("user", DataBase.findUserById(userId));
        }

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView modify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User updateUser = JsonUtils.toObject(request.getReader(), User.class);

        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        user.update(updateUser);
        DataBase.addUser(user);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(SC_OK);
        return modelAndView;
    }
}
