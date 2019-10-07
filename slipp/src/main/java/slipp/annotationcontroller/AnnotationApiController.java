package slipp.annotationcontroller;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JsonView;
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
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

@Controller
public class AnnotationApiController {
    private static final Logger log = LoggerFactory.getLogger(AnnotationApiController.class);

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = extractPostRequestBody(request);
        log.debug("User : {}", user);

        DataBase.addUser(user);
        response.setStatus(201);
        response.addHeader("Location", "/api/user?userId="+user.getUserId());
        return new ModelAndView(new JsonView());
    }

    User extractPostRequestBody(HttpServletRequest request) throws IOException {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
            return s.hasNext() ? JsonUtils.toObject(s.next(), User.class) : null;
        }
        return null;
    }
}
