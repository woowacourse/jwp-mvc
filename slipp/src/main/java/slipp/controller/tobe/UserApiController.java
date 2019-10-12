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
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView queryUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", DataBase.findUserById(userId));
        log.debug("user query {}", userId);
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = getBody(request);
        UserCreatedDto user = JsonUtils.toObject(body, UserCreatedDto.class);

        DataBase.addUser(new User(user.getUserId(), user.getPassword(), user.getName(), user.getEmail()));
        log.debug("user created {}", user.getUserId());

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/api/users?userId=" + user.getUserId());
        return "/home.jsp";
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView modifyUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String body = getBody(request);
        UserUpdatedDto userInfo = JsonUtils.toObject(body, UserUpdatedDto.class);

        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        user.update(new User(userId, userInfo.getPassword(), userInfo.getName(), userInfo.getEmail()));
        log.debug("user updated {}", userId);

        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", user);
        return mav;
    }

    private static String getBody(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        char[] body = new char[contentLength];
        br.read(body, 0, contentLength);
        return String.copyValueOf(body);
    }

}
