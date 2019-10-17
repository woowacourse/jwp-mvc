package slipp.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.mvc.view.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setVisibility(
                objectMapper.getSerializationConfig()
                            .getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                            .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
        );
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView create(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final User user = objectMapper.readValue(req.getReader(), User.class);
        DataBase.addUser(user);
        res.setStatus(HttpServletResponse.SC_CREATED);
        res.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return new ModelAndView(new JsonView());
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView read(HttpServletRequest req, HttpServletResponse res) {
        res.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(new JsonView()).addObject("user", DataBase.findUserById(req.getParameter("userId")));
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView update(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final UserCreatedDto dto = objectMapper.readValue(req.getReader(), UserCreatedDto.class);
        Optional.ofNullable(DataBase.findUserById(req.getParameter("userId"))).ifPresent(user -> {
            user.update(dto);
            DataBase.addUser(user);
        });
        res.setStatus(HttpServletResponse.SC_OK);
        return new ModelAndView(new JsonView());
    }
}