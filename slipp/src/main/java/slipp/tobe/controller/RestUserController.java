package slipp.tobe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.support.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class RestUserController {
    private static final Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(request.getInputStream(), User.class);

        logger.debug("User: {}", user.getUserId());
        DataBase.addUser(user);
        response.setStatus(201);
        response.addHeader("Location", "/api/users?userId="+user.getUserId());

        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView retrieve(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        return new ModelAndView().addObject("user", user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");

        ObjectMapper objectMapper = new ObjectMapper();
        User updateUser = objectMapper.readValue(request.getInputStream(), User.class);

        User user = DataBase.findUserById(userId);
        user.update(updateUser);

        return new ModelAndView().addObject("user", updateUser);
    }
}
