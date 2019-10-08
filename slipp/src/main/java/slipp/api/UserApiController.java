package slipp.api;

import nextstep.web.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import slipp.controller2.UserSessionUtils;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @ResponseBody
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<User> create(@RequestBody UserCreatedDto userCreatedDto) {
        logger.debug("UserCreatedDto : {}", userCreatedDto);

        final User user = userCreatedDto.toEntity();
        DataBase.addUser(user);

        final URI uri = URI.create("/api/users/" + user.getUserId());
        return ResponseEntity.created(uri).body(user);
    }

    @ResponseBody
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> show(@PathVariable String userId) {
        final User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(user);
    }

    @ResponseBody
    @RequestMapping(value = "/api/users/{userId}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable String userId,
                                 @RequestBody UserUpdatedDto userUpdatedDto,
                                 HttpServletRequest req) {
        final User user = DataBase.findUserById(userId);

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        final User updateUser = userUpdatedDto.toEntity();
        user.update(updateUser);
        logger.debug("Update User : {}", updateUser);
        return ResponseEntity.ok(user);
    }
}
