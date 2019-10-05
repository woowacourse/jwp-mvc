package slipp;

import nextstep.mvc.*;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerExecutionHandlerAdapter;
import nextstep.mvc.tobe.handleradapter.SimpleControllerHandlerAdapter;
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
        List<HandlerMapping> requestMappings = Arrays.asList(new ManualHandlerMapping(), new AnnotationHandlerMapping("slipp"));
        List<HandlerAdapter> handlerAdapters = Arrays.asList(new SimpleControllerHandlerAdapter(), new HandlerExecutionHandlerAdapter());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(requestMappings, handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}