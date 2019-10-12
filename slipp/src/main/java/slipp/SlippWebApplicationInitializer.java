package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.handler.AnnotationHandlerAdapter;
import nextstep.mvc.tobe.handler.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handler.HandlerAdapters;
import nextstep.mvc.tobe.handler.HandlerMappings;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Collections;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        HandlerMappings handlerMappings = new HandlerMappings(Collections.singletonList(
                new AnnotationHandlerMapping("slipp")));

        HandlerAdapters handlerAdapters = new HandlerAdapters(
                Collections.singletonList(new AnnotationHandlerAdapter())
        );

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}