package slipp.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(method = RequestMethod.GET)
    public Object get(HttpServletRequest request, HttpServletResponse response) {
        log.debug("hello");
        return new RedirectView("/");
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object create(HttpServletRequest request, HttpServletResponse response) {
        UserCreatedDto userCreatedDto = getBody(request, UserCreatedDto.class);

        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        DataBase.addUser(user);
        log.debug("user created {}", user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        return user;
    }

    private static <T> T getBody(HttpServletRequest request, Class<T> returnType) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return JsonUtils.toObject(request.getReader().readLine(), returnType);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
