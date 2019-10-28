package slipp.controller;

import nextstep.mvc.tobe.JsonView;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.utils.JsonUtils;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    @RequestMapping(value = "/api/users", method = {RequestMethod.POST})
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin");

        UserDto userDto = readUserDtoFromBody(request);
        log.debug("createdUser: {}", userDto);

        DataBase.addUser(userDto.toUser());

        response.setHeader("Location", getUserLocation(request, userDto));
        response.setStatus(HttpServletResponse.SC_CREATED);

        ModelAndView modelAndView = new ModelAndView(JsonView.getInstance());
        modelAndView.addObject("message", "created");
        return modelAndView;
    }

    private UserDto readUserDtoFromBody(HttpServletRequest request) {
        String json = getBody(request);
        return JsonUtils.toObject(json, UserDto.class);
    }

    private String getUserLocation(HttpServletRequest request, UserDto userDto) {
        return String.format("%s?userId=%s", request.getRequestURI(), userDto.getUserId());
    }

    @RequestMapping(value = "/api/users", method = {RequestMethod.GET})
    public ModelAndView showUser(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin");

        String userId = request.getParameter("userId");
        log.debug("userId: {}", userId);

        User user = DataBase.findUserById(userId);
        UserDto dto = UserDto.fromUser(user);

        ModelAndView modelAndView = new ModelAndView(JsonView.getInstance());
        modelAndView.addObject("user", dto);
        return modelAndView;
    }

    @RequestMapping(value = "/api/users", method = {RequestMethod.PUT})
    public ModelAndView updateUser(HttpServletRequest request, HttpServletResponse response) {
        log.debug("begin");

        UserUpdatedDto dto = readUserUpdateDtoFromBody(request);
        log.debug("userUpdatedDto: {}", dto);

        updateUser(dto, request.getParameter("userId"));

        ModelAndView modelAndView = new ModelAndView(JsonView.getInstance());
        modelAndView.addObject("message", "updated");
        return modelAndView;
    }

    private UserUpdatedDto readUserUpdateDtoFromBody(HttpServletRequest request) {
        String json = getBody(request);
        return JsonUtils.toObject(json, UserUpdatedDto.class);
    }

    private void updateUser(UserUpdatedDto dto, String userId) {
        log.debug("userId: {}", userId);
        User user = DataBase.findUserById(userId);
        user.update(dto.toUser());
    }

    private String getBody(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            log.error("error: ", e);
            throw new RuntimeException("요청의 바디를 읽을 수 없습니다.");
        }
    }
}
