package slipp.controller;

import nextstep.mvc.tobe.resolver.ArgumentResolver;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.support.ResponseLocationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = ArgumentResolver.resolve(req, User.class);
        DataBase.addUser(user);
        log.debug("create user >>> {}", user);

        String location = ResponseLocationBuilder.of("/api/users")
                .appendParam("userId", user.getUserId())
                .build();
        resp.setHeader("Location", location);
        resp.setStatus(HttpServletResponse.SC_CREATED);

        return new ModelAndView(new JsonView());
    }
}
