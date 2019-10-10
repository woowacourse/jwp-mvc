package slipp.service;

import slipp.domain.User;
import slipp.exception.UserNotFoundException;
import slipp.support.db.DataBase;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    public void addUser(User user) {
        DataBase.addUser(user);
    }


    public User findByUserId(String userId) throws UserNotFoundException {
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }

    public List<User> findAll() {
        return new ArrayList<>(DataBase.findAll());
    }
}
