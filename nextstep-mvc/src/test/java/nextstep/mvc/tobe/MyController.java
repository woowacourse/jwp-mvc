package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.web.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class MyController {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView findUserId(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        logger.debug("Find UserId : {}", userId);
        User user = DataBase.findUserById(userId);
        request.setAttribute("user", user);
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
                request.getParameter("userId"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("email"));
        logger.debug("User : {}", user);
        DataBase.addUser(user);
        return null;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public Object findUser(@RequestParam(value = "userId") String userId,
                           HttpServletRequest request, HttpServletResponse response) {
        Optional<User> maybeUser = Optional.ofNullable(DataBase.findUserById(userId));
        logger.debug("find User");

        return maybeUser.orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public Object createUser(@RequestBody User user, HttpServletResponse response) {
        DataBase.addUser(user);
        return null;
    }
}
