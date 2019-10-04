package nextstep.mvc.tobe.test;

import nextstep.db.DataBase;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {
    private static final Logger logger = LoggerFactory.getLogger(MyController.class);

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String findUserId(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        logger.debug("Find UserId : {}", userId);
        User user = DataBase.findUserById(userId);
        request.setAttribute("user", user);
        return "/users/view.jsp";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String save(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(
            request.getParameter("userId"),
            request.getParameter("password"),
            request.getParameter("name"),
            request.getParameter("email"));
        logger.debug("User : {}", user);
        DataBase.addUser(user);
        return "/home.jsp";
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("test", true);
        return "/test.jsp";
    }

    @RequestMapping("/no_name")
    public String test_no_attribute_name(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("test_no_value_name", true);
        return "/home.jsp";
    }

    @RequestMapping
    public String test_empty_url(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("test_emtpy_url", true);
        return "/home.jsp";
    }
}
