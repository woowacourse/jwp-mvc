package slipp.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static nextstep.web.annotation.RequestMethod.*;

@Controller
public class UserApiController {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @RequestMapping(value = "/api/users", method = POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = OBJECT_MAPPER.readValue(request.getInputStream(), User.class);
            DataBase.addUser(user);
            response.setHeader("Location", "/api/users?userId=" + user.getUserId());
            response.setStatus(HttpServletResponse.SC_CREATED);
            return new ModelAndView("jsonView");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/api/users", method = GET)
    public ModelAndView read(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        ModelAndView modelAndView = new ModelAndView("jsonView");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = OBJECT_MAPPER.readValue(request.getInputStream(), User.class);
            DataBase.findUserById(request.getParameter("userId")).update(user);
            return new ModelAndView("jsonView");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
