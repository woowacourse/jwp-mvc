package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);
    private static final String[] BASE_PACKAGE = {"slipp"};

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(new AnnotationHandlerMapping(BASE_PACKAGE));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}