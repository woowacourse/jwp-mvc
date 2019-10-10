package slipp.controller;

import nextstep.mvc.tobe.resolver.HttpRequestBodyResolver;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import nextstep.web.support.ResponseLocationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserApiController {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView save(HttpServletRequest req, HttpServletResponse resp) {
        User user = HttpRequestBodyResolver.resolve(req, User.class);
        DataBase.addUser(user);
        log.debug("create user >>> {}", user);

        String location = ResponseLocationBuilder.of("/api/users")
                .appendParam("userId", user.getUserId())
                .build();
        resp.setHeader("Location", location);
        resp.setStatus(HttpServletResponse.SC_CREATED);

        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView fetch(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        log.debug("fetch user >>> {}", user);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        UserUpdatedDto userUpdatedDto = HttpRequestBodyResolver.resolve(req, UserUpdatedDto.class);

        User user = DataBase.findUserById(userId);
        user.update(userUpdatedDto);
        log.debug("update user >>> {}", userUpdatedDto);

        return new ModelAndView(new JsonView());
    }
}
