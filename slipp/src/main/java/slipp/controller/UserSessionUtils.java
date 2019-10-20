package slipp.controller;

import slipp.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserSessionUtils {
    public static final String USER_SESSION_KEY = "user";

    public static User getUserFromSession(HttpSession session) {
        final Object user = session.getAttribute(USER_SESSION_KEY);
        return user == null ? null : (User) user;
    }

    public static boolean isLogined(HttpSession session) {
        return getUserFromSession(session) != null;
    }

    public static boolean isSameUser(HttpSession session, User user) {
        return Optional.ofNullable(getUserFromSession(session)).flatMap(u -> Optional.ofNullable(user)
                                                                .map(u::isSameUser))
                                                                .orElse(false);
    }
}