package nextstep.mvc.tobe;

import nextstep.db.DataBase;
import nextstep.mvc.view.RedirectView;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestUserController {
    private static final Logger logger = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        logger.debug("userId: {}, password: {}", userId, password);
        ModelAndView mav = new ModelAndView();
        mav.addObject("userId", userId);
        mav.addObject("password", password);
        return mav;
    }

    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public ModelAndView signup(HttpServletRequest req, HttpServletResponse res) {
        DataBase.addUser(
                new User(
                        req.getParameter("userId"),
                        req.getParameter("password"),
                        req.getParameter("name"),
                        req.getParameter("email")
                )
        );
        return new ModelAndView(new RedirectView("redirect:/"));
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUser testUser) {
        logger.debug("testUser: {}", testUser);
        ModelAndView mav = new ModelAndView();
        mav.addObject("testUser", testUser);
        return mav;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        logger.debug("userId: {}", id);
        ModelAndView mav = new ModelAndView();
        mav.addObject("id", id);
        return mav;
    }
}
