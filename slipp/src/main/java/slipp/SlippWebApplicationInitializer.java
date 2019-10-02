package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SlippWebApplicationInitializer  implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        DispatcherServlet dispatcherServlet1 = new DispatcherServlet(new ManualHandlerMapping());
        DispatcherServlet dispatcherServlet1 = new DispatcherServlet(new AnnotationHandlerMapping("slipp"));

        ServletRegistration.Dynamic dispatcher1 = servletContext.addServlet("dispatcher", dispatcherServlet1);

        dispatcher1.setLoadOnStartup(1);
        dispatcher1.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}