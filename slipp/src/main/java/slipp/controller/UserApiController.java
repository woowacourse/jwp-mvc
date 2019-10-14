package slipp.controller;

import nextstep.mvc.ObjectMapperException;
import nextstep.mvc.view.JsonView;
import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.TextView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(method = RequestMethod.POST, value = "/api/users")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        String json = getJsonFromRequestBody(request);

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

    @RequestMapping(method = RequestMethod.PUT, value = "/api/users")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        String json = getJsonFromRequestBody(request);
        User updateUser = JsonUtils.toObject(json, UserUpdatedDto.class).toEntity(userId);
        DataBase.findUserById(userId).update(updateUser);

        return new ModelAndView(new TextView(""));
    }


    private String getJsonFromRequestBody(HttpServletRequest request) {
        try {
            return request.getReader().lines()
                    .collect(Collectors.joining());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ObjectMapperException();
        }
    }
}
