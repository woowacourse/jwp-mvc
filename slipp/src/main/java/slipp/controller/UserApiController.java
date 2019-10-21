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
public class UserApiController {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String URL_LOCATION = "/api/users";

    @RequestMapping(value = URL_LOCATION, method = RequestMethod.GET)
    public ModelAndView getUser(final HttpServletRequest request, final HttpServletResponse response) {
        final User user = findUserById(request);
        return new ModelAndView(new JsonView()).addObject("user", user);
    }

    @RequestMapping(value = URL_LOCATION, method = RequestMethod.POST)
    public ModelAndView createUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final UserCreatedDto dto = MAPPER.readValue(request.getReader(), UserCreatedDto.class);
        final User user = new User(dto.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        DataBase.addUser(user);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = URL_LOCATION, method = RequestMethod.PUT)
    public ModelAndView editUser(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final UserUpdatedDto dto = MAPPER.readValue(request.getReader(), UserUpdatedDto.class);
        final User user = findUserById(request);
        final User updatedUser = new User(user.getUserId(), dto.getPassword(), dto.getName(), dto.getEmail());
        DataBase.addUser(updatedUser);
        return new ModelAndView(new JsonView());
    }

    private User findUserById(final HttpServletRequest request) {
        return DataBase.findUserById(request.getParameter("userId"));
    }
}
