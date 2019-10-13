package slipp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;

public class CreateUserService {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserService.class);

    public String addUser(HttpServletRequest request) {
        User user = new User(request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));

        logger.debug("User : {}", user);

        DataBase.addUser(user);
        return user.getUserId();
    }
}
