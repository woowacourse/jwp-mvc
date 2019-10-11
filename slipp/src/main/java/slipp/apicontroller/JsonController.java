package slipp.apicontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.view.JsonView;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Controller
public class JsonController {
    private static final Logger logger = LoggerFactory.getLogger(JsonController.class);
    private static final String USER_API_URL = "/api/users";
    private static final String LOCATION = "Location";
    private static final String USER_MODEL_KEY = "user";

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = USER_API_URL, method = RequestMethod.GET)
    public ModelAndView read(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");

        logger.info("read user {}", userId);
        User user = DataBase.findUserById(userId);

        ModelAndView mav = ModelAndView.of(new JsonView())
                .addObject("user", user);

        return mav;
    }

    @RequestMapping(value = USER_API_URL, method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();
        String body = getBody(inputStream);

        User user = objectMapper.readValue(body, User.class);
        DataBase.addUser(user);

        logger.info("create user {}", user.getUserId());

        response.addHeader(LOCATION, USER_API_URL + "?userId=" + user.getUserId());
        response.setStatus(HttpStatus.CREATED.value());
        ModelAndView mav = ModelAndView.of(new JsonView())
                .addObject(USER_MODEL_KEY, user);;

        return mav;
    }

    @RequestMapping(value = USER_API_URL, method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        logger.info("update user {}", userId);

        User user = DataBase.findUserById(userId);

        String body = getBody(request.getInputStream());
        User updateUser = objectMapper.readValue(body, User.class);

        user.update(updateUser);

        ModelAndView mav = ModelAndView.of(new JsonView())
                .addObject("user", user);;

        return mav;
    }

    private String getBody(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            char[] buffer = new char[128];
            int readBytes = -1;
            while((readBytes = br.read(buffer)) > 0) {
                stringBuilder.append(buffer,0, readBytes);
            }
        }

        return stringBuilder.toString();
    }
}