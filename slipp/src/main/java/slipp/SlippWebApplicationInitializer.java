package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ControllerHandlerAdapter;
import nextstep.mvc.tobe.HandlerAdapter;
import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.HandlerExecutionAdaptor;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdaptor()),
            Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp")) );
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}