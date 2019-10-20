package nextstep.mvc.tobe;

import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestUserController {
    private static final Logger logger = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        logger.debug("userId: {}, password: {}", userId, password);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("password", password);
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_int_long(long id, int age) {
        logger.debug("id: {}, age: {}", id, age);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("id", id);
        modelAndView.addObject("age", age);
        return modelAndView;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create_javabean(TestUser testUser) {
        logger.debug("testUser: {}", testUser);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("testUser", testUser);
        return modelAndView;
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        logger.debug("userId: {}", id);
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("id", id);
        return modelAndView;
    }
}
