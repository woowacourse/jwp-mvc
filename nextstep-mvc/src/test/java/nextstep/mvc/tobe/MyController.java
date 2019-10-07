package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.OPTIONS})
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

    @RequestMapping(value = "/users/nothing")
    public ModelAndView nothing(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/users/{id}/{userId}", method = RequestMethod.GET)
    public ModelAndView testMethod(@PathVariable long id, @PathVariable long userId) {
        logger.debug("id: {},userId: {}", id, userId);
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id", id);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }
}
