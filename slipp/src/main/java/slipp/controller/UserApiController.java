package slipp.controller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.utils.JsonUtils;
import nextstep.utils.UriBuilder;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;
import slipp.controller.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {
    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        User user = JsonUtils.requestBodyToObject(req, User.class);
        String location = UriBuilder.builder(req.getRequestURI())
                .appendQueryParams("userId", user.getUserId())
                .build();

        DataBase.addUser(user);

        resp.setHeader("location", location);
        resp.setStatus(SC_CREATED);
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        checkIfExisting(user);
        return createModelAndView(user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        User updateUser = JsonUtils.requestBodyToObject(req, User.class);

        user.update(updateUser);
        return createModelAndView(user);
    }

    private void checkIfExisting(User user) {
        if (user == null) {
            throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
        }
    }

    private ModelAndView createModelAndView(User user) {
        ModelAndView model = new ModelAndView(new JsonView());
        model.addObject("user", user);
        return model;
    }
}
