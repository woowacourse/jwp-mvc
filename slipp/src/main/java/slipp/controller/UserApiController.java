package slipp.controller;

import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.TextView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class UserApiController {

    @RequestMapping(method = RequestMethod.POST, value = "/api/users")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = request.getReader().lines()
                .collect(Collectors.joining());

        User user = JsonUtils.toObject(json, UserCreatedDto.class).toEntity();
        DataBase.addUser(user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new TextView(""));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/api/users")
    public ModelAndView find(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");

        User user = DataBase.findUserById(userId);
        ModelAndView mv = new ModelAndView(new JsonView());
        mv.addObject("user", user);
        return mv;
    }
}
