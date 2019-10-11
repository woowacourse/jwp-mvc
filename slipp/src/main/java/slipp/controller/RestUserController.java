package slipp.controller;

import com.google.gson.Gson;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.CreatedView;
import nextstep.mvc.tobe.view.JsonView;
import nextstep.mvc.tobe.view.View;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;
import slipp.dto.UserCreatedDto;
import slipp.dto.UserUpdatedDto;
import slipp.support.db.DataBase;

@Controller
public class RestUserController {
    private static final String REST_USER_API_PATH = "/api/users";
    private static final String QUERY_PARAM_USER_ID = "userId";
    private final Logger logger = LoggerFactory.getLogger(RestUserController.class);


    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.POST)
    public View create(UserCreatedDto userCreatedDto) {
        String userId = saveUser(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        String location = REST_USER_API_PATH + "?" + QUERY_PARAM_USER_ID + "=" + userId;
        return new CreatedView(location);
    }

    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.GET)
    public ModelAndView retrieve(String userId) {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", getUser(userId));
        return modelAndView;
    }

    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.PUT)
    public ModelAndView update(String userId, UserUpdatedDto userUpdatedDto) {
        User user = updateUser(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail());
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    private Object convertObject(String body, Class<?> clazz) {
        return new Gson().fromJson(body, clazz);
    }

    private String saveUser(String userId, String password, String name, String email) {
        User user = new User(userId, password, name, email);
        logger.debug("user : {}", user);
        DataBase.addUser(user);
        return user.getUserId();
    }

    private User getUser(String userId) {
        return DataBase.findUserById(userId);
    }

    private User updateUser(String userId, String password, String name, String email) {
        User preUser = getUser(userId);
        User updatedUser = new User(preUser.getUserId(), password, name, email);
        DataBase.addUser(updatedUser);
        return updatedUser;
    }
}
