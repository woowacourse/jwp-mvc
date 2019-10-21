package slipp.service;

import slipp.domain.User;
import slipp.dto.UserRequestLoginDto;
import slipp.support.db.DataBase;

import java.util.Optional;

public class LoginService {
    public boolean matchLoginData(UserRequestLoginDto loginDto) {
        String requestId = loginDto.getUserId();
        String requestPassword = loginDto.getPassword();

        Optional<User> maybeUser = Optional.ofNullable(DataBase.findUserById(requestId));

        return maybeUser.filter(user -> user.matchPassword(requestPassword))
                        .isPresent();
    }
}
