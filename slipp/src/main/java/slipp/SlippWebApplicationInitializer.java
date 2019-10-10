package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handleradapter.ControllerHandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerExecutionHandlerAdapter;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        final DispatcherServlet dispatcherServlet = new DispatcherServlet(
                Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp.controller")),
                Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionHandlerAdapter()));

        final ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        logger.info("Start MyWebApplication Initializer");
    }
}