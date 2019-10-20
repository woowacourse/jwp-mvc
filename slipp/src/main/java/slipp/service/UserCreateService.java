package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

public class UserCreateService {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateService.class);

    public String addUser(UserCreatedDto userCreatedDto) {
        User user = new User(userCreatedDto.getUserId(),
                            userCreatedDto.getPassword(),
                            userCreatedDto.getName(),
                            userCreatedDto.getEmail());
        logger.debug("Create user : {}", user);

        DataBase.addUser(user);

        return user.getUserId();
    }
}
