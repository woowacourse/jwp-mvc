package slipp.api;

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

import static nextstep.utils.JsonUtils.OBJECT_MAPPER;

@Controller
public class UserController {

    private static final int OK = 200;
    private static final int CREATED = 201;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView select(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setStatus(OK);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        UserCreatedDto userCreatedDto = OBJECT_MAPPER.readValue(request.getReader(), UserCreatedDto.class);
        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());

//        User user = userCreatedDto.toEntity();

        DataBase.addUser(user);

        response.setStatus(CREATED);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());

        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        UserUpdatedDto userUpdatedDto = OBJECT_MAPPER.readValue(request.getReader(), UserUpdatedDto.class);
        user.update(userUpdatedDto.toEntity());

        response.setStatus(OK);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
