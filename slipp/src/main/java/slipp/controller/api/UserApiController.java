package slipp.controller.api;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static slipp.controller.api.UserApiController.DEFAULT_MAPPING_VALUE;

@RestController
@RequestMapping(DEFAULT_MAPPING_VALUE)
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    private static final String LOCATION = "Location";
    static final String DEFAULT_MAPPING_VALUE = "/api/users";

    @RequestMapping(method = RequestMethod.GET)
    public Object findUser(@RequestParam(value = "userId") String userId,
                           HttpServletRequest request, HttpServletResponse response) {
        User user = getUser(userId);
        log.debug("find User");

        return new ModelAndView().addObject("user", user);
    }


    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody UserCreatedDto userCreatedDto, HttpServletResponse response) {

        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        DataBase.addUser(user);
        log.debug("user created {}", user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(LOCATION, DEFAULT_MAPPING_VALUE + "?userId=" + user.getUserId());
        return user;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Object create(@RequestParam(value = "userId") String userId, @RequestBody UserUpdatedDto userUpdatedDto, HttpServletResponse response) {
        User user = getUser(userId);
        User updateUser = new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail());
        user.update(updateUser);
        DataBase.addUser(user);
        log.debug("user created {}", user);

        return user;
    }

    private User getUser(@RequestParam("userId") String userId) {
        return Optional.ofNullable(DataBase.findUserById(userId)).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }
}
