package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView select(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        modelAndView.addObject("user", user);
        response.setStatus(200);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ObjectMapper objectMapper = new ObjectMapper();
        UserCreatedDto userCreatedDto = objectMapper.readValue(request.getReader(), UserCreatedDto.class);
        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        DataBase.addUser(user);
        response.setStatus(201);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        response.setStatus(200);
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        ObjectMapper objectMapper = new ObjectMapper();
        UserUpdatedDto userUpdatedDto = objectMapper.readValue(request.getReader(), UserUpdatedDto.class);
        user.update(new User(user.getUserId(), userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail()));

        modelAndView.addObject("user", user);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return modelAndView;
    }
}
