//package slipp.annotationcontroller;
//
//import nextstep.web.annotation.Controller;
//import nextstep.web.annotation.RequestMapping;
//import nextstep.web.annotation.RequestMethod;
//import slipp.support.db.DataBase;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//public class AnnotationHomeController {
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index(HttpServletRequest req, HttpServletResponse resp) {
//        req.setAttribute("users", DataBase.findAll());
//        return "home.jsp";
//    }
//}
