package slipp.service;

import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.exception.NotFoundUserException;
import slipp.support.db.DataBase;

import java.util.Optional;

public class UserSearchService {

    public UserCreatedDto findUserById(String userId) {
        Optional<User> maybeUser = Optional.ofNullable(DataBase.findUserById(userId));

        if (maybeUser.isPresent()) {
            User user = maybeUser.get();
            return new UserCreatedDto(user.getUserId(),
                    user.getPassword(),
                    user.getName(),
                    user.getEmail());
        }

        throw new NotFoundUserException();
    }
}
