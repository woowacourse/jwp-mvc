package slipp;

import nextstep.mvc.*;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(initHandlerMapping(), initHandlerAdapter());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

    private List<HandlerMapping> initHandlerMapping() {
        List<HandlerMapping> handlerMappings = new ArrayList<>();
        handlerMappings.add(new ManualHandlerMapping());
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
        return handlerMappings;
    }

    private List<HandlerAdapter> initHandlerAdapter() {
        List<HandlerAdapter> handlerAdapters = new ArrayList<>();
        handlerAdapters.add(new ManualHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
        return handlerAdapters;
    }
}