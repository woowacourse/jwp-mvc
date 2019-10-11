package slipp.apicontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class JsonController {
    private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

    private static final String USER_API_URL = "/api/users";
    private static final String LOCATION = "Location";
    private static final String USER_MODEL_KEY = "user";
    private static final String USER_ID_PARAMETER_KEY = "userId";

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = USER_API_URL, method = RequestMethod.GET)
    public ModelAndView read(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter(USER_ID_PARAMETER_KEY);

        logger.info("read user {}", userId);
        User user = DataBase.findUserById(userId);

        return ModelAndView.json()
                .addObject(USER_MODEL_KEY, user);
    }

    @RequestMapping(value = USER_API_URL, method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = objectMapper.readValue(request.getInputStream(), User.class);;
        DataBase.addUser(user);

        logger.info("create user {}", user.getUserId());

        response.addHeader(LOCATION, USER_API_URL + "?userId=" + user.getUserId());
        response.setStatus(HttpStatus.CREATED.value());

        return ModelAndView.json()
                .addObject(USER_MODEL_KEY, user);
    }

    @RequestMapping(value = USER_API_URL, method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter(USER_ID_PARAMETER_KEY);
        logger.info("update user {}", userId);

        User user = DataBase.findUserById(userId);
        User updateUser = objectMapper.readValue(request.getInputStream(), User.class);
        user.update(updateUser);

        return ModelAndView.json()
                .addObject(USER_MODEL_KEY, user);
    }
}