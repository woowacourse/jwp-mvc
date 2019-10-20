package slipp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;

import javax.servlet.http.HttpServletRequest;

public class UserRequestUtils {
    private static final Logger log = LoggerFactory.getLogger(UserRequestUtils.class);

    public static User getUser(HttpServletRequest req) {
        User user = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        log.debug("User : {}", user);
        return user;
    }

    public static String getUserId(HttpServletRequest req) {
        String userId = req.getParameter("userId");
        log.debug("userId: {}", userId);
        return userId;
    }

    public static String getPassword(HttpServletRequest req) {
        String password = req.getParameter("password");
        log.debug("password: {}", password);
        return password;
    }
}
