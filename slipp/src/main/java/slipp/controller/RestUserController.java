package slipp.controller;

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
import slipp.service.RestUserService;

@Controller
public class RestUserController {
    private final Logger logger = LoggerFactory.getLogger(RestUserController.class);
    private static final String REST_USER_API_PATH = "/api/users";
    private static final String QUERY_PARAM_USER_ID = "userId";

    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.POST)
    public View create(UserCreatedDto userCreatedDto) {
        String userId = RestUserService.saveUser(userCreatedDto.getUserId(), userCreatedDto.getPassword(), userCreatedDto.getName(), userCreatedDto.getEmail());
        String location = REST_USER_API_PATH + "?" + QUERY_PARAM_USER_ID + "=" + userId;
        return new CreatedView(location);
    }

    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.GET)
    public ModelAndView retrieve(String userId) {
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", RestUserService.getUser(userId));
        return modelAndView;
    }

    @RequestMapping(value = REST_USER_API_PATH, method = RequestMethod.PUT)
    public ModelAndView update(String userId, UserUpdatedDto userUpdatedDto) {
        User user = RestUserService.updateUser(userId, userUpdatedDto.getPassword(), userUpdatedDto.getName(), userUpdatedDto.getEmail());
        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
