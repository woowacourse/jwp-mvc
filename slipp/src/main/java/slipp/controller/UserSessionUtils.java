package slipp.controller;

import slipp.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserSessionUtils {
    public static final String USER_SESSION_KEY = "user";

    public static User getUserFromSession(HttpSession session) {
        return Optional.ofNullable((User) session.getAttribute(USER_SESSION_KEY))
                .orElse(null);
    }

    public static boolean isLoggedIn(HttpSession session) {
        if (getUserFromSession(session) == null) {
            return false;
        }
        return true;
    }

    public static boolean isSameUser(HttpSession session, User user) {
        if (!isLoggedIn(session)) {
            return false;
        }

        if (user == null) {
            return false;
        }

        return user.isSameUser(getUserFromSession(session));
    }
}
