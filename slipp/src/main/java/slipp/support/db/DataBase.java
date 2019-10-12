package slipp.support.db;

import com.google.common.collect.Maps;
import slipp.domain.User;

import java.util.Collection;
import java.util.Map;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("admin", new User("admin", "password", "자바지기", "admin@slipp.net"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void replace(User updatedUser) {
        if (!users.keySet().contains(updatedUser.getUserId())) {
            throw new UnfoundUserException();
        }
        users.replace(updatedUser.getUserId(), updatedUser);
    }
}
