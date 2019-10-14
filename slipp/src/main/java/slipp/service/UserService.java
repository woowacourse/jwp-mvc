package slipp.service;

import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

public class UserService {

    public void createUser(final UserCreatedDto userCreatedDto) {
        DataBase.addUser(userCreatedDto.toEntity());
    }
}
