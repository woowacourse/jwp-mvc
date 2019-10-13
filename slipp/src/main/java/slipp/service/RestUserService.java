package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

public class RestUserService {
    private static final Logger logger = LoggerFactory.getLogger(RestUserService.class);

    public static String saveUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        logger.debug("user : {}", user);
        DataBase.addUser(user);
        return user.getUserId();
    }

    public static User getUser(String userId) {
        return DataBase.findUserById(userId);
    }

    public static User updateUser(String userId, String password, String name, String email) {
        User preUser = getUser(userId);
        User updatedUser = new User(preUser.getUserId(), password, name, email);
        DataBase.addUser(updatedUser);
        return updatedUser;
    }
}
