package slipp;

import nextstep.ControllerHandlerAdapter;
import nextstep.HandlerAdapter;
import nextstep.HandlerExecutionAdapter;
import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        List<HandlerAdapter> adapters = Arrays.asList(new ControllerHandlerAdapter(), new HandlerExecutionAdapter());

        DispatcherServlet dispatcherServlet = new DispatcherServlet(adapters, new AnnotationHandlerMapping("slipp"));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}