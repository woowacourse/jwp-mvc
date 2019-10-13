package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.mvc.handlermapping.InOrderHandlerMapping;
import nextstep.mvc.handlermapping.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.HomeController;
import slipp.controller.handleradapter.ControllerAdaptor;
import slipp.controller.handleradapter.HandlerExecutionAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        HandlerMapping mapping = InOrderHandlerMapping.from(Arrays.asList(
                ManualHandlerMapping.builder()
                        .urlAndController("/", new HomeController())
                        .build(),
                new AnnotationHandlerMapping("slipp")));
        List<HandlerAdapter> adapters = Arrays.asList(
                HandlerExecutionAdapter.getInstance(),
                ControllerAdaptor.getInstance()
        );
        DispatcherServlet dispatcherServlet = new DispatcherServlet(mapping, adapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}