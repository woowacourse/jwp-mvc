package slipp.controller.api;

import nextstep.mvc.tobe.ResponseEntity;
import nextstep.web.annotation.*;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import static nextstep.web.annotation.RequestMethod.*;

@Controller
public class UserApiController {
    @RequestMapping(value = "/api/users", method = POST)
    @ResponseBody
    public ResponseEntity create(@RequestBody UserCreatedDto userCreatedDto) {
        User user = new User(userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail());

        DataBase.addUser(user);
        return ResponseEntity.created()
                .location("/api/users?userId=" + user.getUserId())
                .build();
    }

    @RequestMapping(value = "/api/users", method = GET)
    @ResponseBody
    public ResponseEntity<User> read(@RequestParam("userId") String userId) {
        User user = DataBase.findUserById(userId);

        return ResponseEntity.ok()
                .body(user);
    }

    @RequestMapping(value = "/api/users", method = PUT)
    @ResponseBody
    public ResponseEntity update(@RequestBody UserUpdatedDto userUpdatedDto, @RequestParam("userId") String userId) {
        User user = new User(userId,
                userUpdatedDto.getPassword(),
                userUpdatedDto.getName(),
                userUpdatedDto.getEmail());
        DataBase.findUserById(userId).update(user);

        return ResponseEntity.ok()
                .build();
    }
}
