package slipp.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.PathVariable;
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
import java.util.Optional;

import static slipp.controller.api.UserApiController.DEFAULT_MAPPING_VALUE;

@RestController
@RequestMapping(DEFAULT_MAPPING_VALUE)
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    private static final String LOCATION = "Location";
    static final String DEFAULT_MAPPING_VALUE = "/api/users";

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Object findUser(@PathVariable(value = "userId") String userId,
                           HttpServletRequest request, HttpServletResponse response) {
        Optional<User> maybeUser = Optional.ofNullable(DataBase.findUserById(userId));
        log.debug("find User");

        return maybeUser.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object create(HttpServletRequest request, HttpServletResponse response) {
        UserCreatedDto userCreatedDto = getBody(request, UserCreatedDto.class);

        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        DataBase.addUser(user);
        log.debug("user created {}", user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(LOCATION, DEFAULT_MAPPING_VALUE + "/" + user.getUserId());
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
