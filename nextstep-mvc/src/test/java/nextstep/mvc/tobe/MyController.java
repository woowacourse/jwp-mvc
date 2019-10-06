package nextstep.mvc.tobe;

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

    @RequestMapping(value = "/notnull", method = RequestMethod.POST)
    public ModelAndView returnNotNull(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/method", method = RequestMethod.POST)
    public ModelAndView handlePost(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/method")
    public ModelAndView cannotHandlePost(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/one-method", method = RequestMethod.POST)
    public ModelAndView handleOneRequestMethod(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }

    @RequestMapping(value = "/all-method")
    public ModelAndView handleAllRequestMethods(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView();
    }
}
