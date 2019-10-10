package slipp.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.JsonParserSequence;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class RestUserController {
    private static final Logger logger = LoggerFactory.getLogger(RestUserController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug(System.lineSeparator());
        String body;
        User user = new User();
        try {
            body = request.getReader()
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            logger.debug(body);
            user = objectMapper.readValue(body, User.class);
            DataBase.addUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ModelAndView("redirect:/users/" + user.getUserId());
    }
//
//    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
//    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
//    }
}
