package slipp.controller;

import nextstep.mvc.view.ModelAndView;
import nextstep.mvc.view.TextView;
import nextstep.web.annotation.Controller;
import nextstep.web.annotation.RequestMapping;
import nextstep.web.annotation.RequestMethod;
import slipp.domain.User;
import slipp.support.db.DataBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserApiController {

    @RequestMapping(method = RequestMethod.POST, value = "/api/users")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) {
        User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"), request.getParameter("email"));

        DataBase.addUser(user);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader("Location", "/api/users");
        return new ModelAndView(new TextView(""));
    }
}
