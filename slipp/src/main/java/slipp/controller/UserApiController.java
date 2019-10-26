package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.exception.UserCreationFailException;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);
    private static final String LOCATION = "location";

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse resp) {
        User user = createUser(req);

        DataBase.addUser(user);

        resp.setStatus(SC_CREATED);
        resp.setHeader(LOCATION, req.getRequestURI() + createQueryParams(user));
        return new ModelAndView(new JsonView());
    }

    private User createUser(HttpServletRequest req) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String requestBody = req.getReader().readLine();
            User created = objectMapper.readValue(requestBody, User.class);
            logger.debug("User : {}", created);
            return created;
        } catch (IOException e) {
            throw new UserCreationFailException(e.getCause());
        }
    }

    private String createQueryParams(User user) {
        return "?userId=" + user.getUserId();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView find(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);

        checkIfExisting(user);
        return createModelAndView(user);
    }

    private void checkIfExisting(User user) {
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
    }

    private ModelAndView createModelAndView(User user) {
        ModelAndView model = new ModelAndView(new JsonView());
        model.addObject("user", user);
        return model;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        User updateUser = createUpdatedUser(req);

        user.update(updateUser);

        return createModelAndView(user);
    }

    private User createUpdatedUser(HttpServletRequest req) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String requestBody = req.getReader().readLine();
            User updated = objectMapper.readValue(requestBody, User.class);
            logger.debug("Updated user : {}", updated);
            return updated;
        } catch (IOException e) {
            throw new UserCreationFailException(e.getCause());
        }
    }
}
