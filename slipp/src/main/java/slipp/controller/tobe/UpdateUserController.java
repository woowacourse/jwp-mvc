package slipp.controller.tobe;

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
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class UpdateUserController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateUserController.class);

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
        } catch (IOException e) {
            logger.debug(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        logger.debug("Update user : {}", user);
        return new ModelAndView();
    }

    private static String getBody(HttpServletRequest request) throws IOException {
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }
}
