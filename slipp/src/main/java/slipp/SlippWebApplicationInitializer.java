package slipp;

import nextstep.mvc.asis.Controller;
import nextstep.mvc.handleradapter.ControllerAdaptor;
import nextstep.mvc.handleradapter.HandlerAdapterWrappers;
import nextstep.mvc.handleradapter.HandlerExecutionAdapter;
import nextstep.mvc.handlermapping.*;
import nextstep.mvc.DispatcherServlet;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.HomeController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        HandlerMapping mapping = InOrderHandlerMapping.from(Arrays.asList(
                ManualHandlerMapping.builder()
                        .urlAndController("/", new HomeController())
                        .build(),
                new AnnotationHandlerMapping("slipp")));

        mapping.initialize();
        DispatcherServlet dispatcherServlet = DispatcherServlet.from(
                mapping,
                HandlerAdapterWrappers.builder()
                        .addWrapper(Controller.class, controller -> ControllerAdaptor.from(controller))
                        .addWrapper(HandlerExecution.class, execution -> HandlerExecutionAdapter.from(execution))
                        .build()
        );

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}