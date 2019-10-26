package slipp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.EmptyView;
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

@nextstep.web.annotation.Controller
public class CreateUserController {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserController.class);
    public static final String LOCATION = "location";

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = createUser(req);

        DataBase.addUser(user);

        resp.setStatus(SC_CREATED);
        resp.setHeader(LOCATION, req.getRequestURI() + createQueryParams(user));
        return new ModelAndView(new EmptyView());
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
}
