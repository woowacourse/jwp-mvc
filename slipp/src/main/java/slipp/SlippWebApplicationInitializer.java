package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.adapter.HandlerExecutionHandlerAdapter;
import nextstep.mvc.tobe.mapping.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Collections;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(
                Collections.singletonList(new AnnotationHandlerMapping("slipp.controller")),
                Collections.singletonList(new HandlerExecutionHandlerAdapter()));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}