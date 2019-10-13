package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;

public class UserCreateService {
    private static final Logger logger = LoggerFactory.getLogger(UserCreateService.class);

    public UserCreatedDto addUser(HttpServletRequest request) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        logger.debug("User : {}", user);

        DataBase.addUser(user);
        return new UserCreatedDto(user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }
}
