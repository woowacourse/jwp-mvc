package slipp;

import nextstep.mvc.AnnotationHandlerAdapter;
import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.ManualHandlerAdapter;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        AnnotationHandlerMapping annotationHandler = new AnnotationHandlerMapping("slipp.controller");
        HandlerAdapter[] handlerAdapters = {new ManualHandlerAdapter(manualHandlerMapping), new AnnotationHandlerAdapter(annotationHandler)};

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}