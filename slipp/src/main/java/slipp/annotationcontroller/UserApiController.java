package slipp.annotationcontroller;

import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.ModelAndView;
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
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
public class UserApiController {
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserCreatedDto userCreatedDto = convertJsonToObject(request, UserCreatedDto.class);
        User user = new User(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        logger.debug("User : {}", user);

        DataBase.addUser(user);

        response.addHeader("Location", "/api/users?userId=" + userCreatedDto.getUserId());
        response.setStatus(201);
        ModelAndView mav = new ModelAndView(new JsonView());
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ModelAndView findUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        logger.debug("UserId : {}", userId);

        User user = DataBase.findUserById(userId);
        logger.debug("user : {}", user);

        response.setStatus(200);
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        UserUpdatedDto userUpdatedDto = convertJsonToObject(request, UserUpdatedDto.class);
        logger.debug("userUpdatedDto : {}", userUpdatedDto);

        User user = DataBase.findUserById(userId);
        user.update(new User(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail()));
        logger.debug("user : {}", user);

        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("user", user);
        return mav;
    }

    private <T> T convertJsonToObject(HttpServletRequest request, Class<T> clazz) throws IOException {
        return JsonUtils.toObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())), clazz);
    }
}
