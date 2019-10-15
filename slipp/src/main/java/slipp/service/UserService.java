package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User create(UserCreatedDto userCreatedDto) {
        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        log.debug("User : {}", user);
        DataBase.addUser(user);
        return user;
    }

    public User findUserById(String userId) {
        return DataBase.findUserById(userId);
    }
}
