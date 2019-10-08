package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserCreatedDto userCreatedDto = JsonUtils.toObject(getBody(request), UserCreatedDto.class);
        User user = new User(userCreatedDto);

        DataBase.addUser(user);

        response.setHeader("Location", request.getRequestURI() + "?userId" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    private String getBody(HttpServletRequest request) throws IOException {
        return request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
