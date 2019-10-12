package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User create(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        log.debug("User : {}", user);
        DataBase.addUser(user);
        return user;
    }
}
