package slipp.controller;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.PathVariable;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;

@Controller
public class TestUserController {
    private static final Logger log = LoggerFactory.getLogger(TestUserController.class);

    @RequestMapping(value = "/test/users/1", method = RequestMethod.POST)
    public ModelAndView create_string(String userId, String password) {
        log.debug("userId: {}, password: {}", userId, password);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/test/users/2", method = RequestMethod.POST)
    public ModelAndView create_int_long(long id, int age) {
        log.debug("id: {}, age: {}", id, age);
        return new ModelAndView(new RedirectView("/"));
    }

    @RequestMapping(value = "/test/users/3", method = RequestMethod.POST)
    public ModelAndView create_javabean(User user) {
        log.debug("testUser: {}", user);
        return new ModelAndView(new RedirectView("/"));
    }


    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ModelAndView show_pathvariable(@PathVariable long id) {
        log.debug("userId: {}", id);
        return new ModelAndView(new RedirectView("/"));
    }
}