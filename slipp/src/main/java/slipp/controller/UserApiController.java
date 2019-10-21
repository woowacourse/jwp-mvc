package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    private static final String USER_ID = "userId";
    private static final String USER_KEY = "user";
    private static final String LOCATION_HEADER = "Location";

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView signUp(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user = JsonUtils.toObject(getBody(request), User.class);
            logger.debug("User : {}", user);

            DataBase.addUser(user);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.addHeader(LOCATION_HEADER, "/api/users?userId=" + user.getUserId());
        } catch (IOException e) {
            logger.debug(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return new ModelAndView();
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView viewUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(USER_ID);

        User user = DataBase.findUserById(userId);
        logger.debug("User : {}", user);

        return new ModelAndView(new JsonView()).addObject(USER_KEY, user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(USER_ID);
        User user = DataBase.findUserById(userId);
        try {
            User updateUser = JsonUtils.toObject(getBody(request), User.class);
            user.update(updateUser);
            logger.debug("Update user : {}", user);
        } catch (IOException e) {
            logger.debug(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return new ModelAndView();
    }

    private static String getBody(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        char[] body = new char[contentLength];

        br.read(body, 0, contentLength);

        return String.copyValueOf(body);
    }
}
