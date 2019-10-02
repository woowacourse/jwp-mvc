package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
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
        ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("slipp.controller");
        List<HandlerMapping> handlerMappings = Arrays.asList(manualHandlerMapping, annotationHandlerMapping);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}