package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.adapter.HandlerAdapterManager;
import nextstep.mvc.tobe.handler.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handler.HandlerMappingManager;
import nextstep.mvc.tobe.viewResolver.ViewResolverManager;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {

        HandlerMappingManager handlerMappingManager = new HandlerMappingManager();
        handlerMappingManager.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerMappingManager.add(new ManualHandlerMapping());

        HandlerAdapterManager handlerAdapterManager = new HandlerAdapterManager();
        handlerAdapterManager.add(new ControllerHandlerAdapter());

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappingManager, handlerAdapterManager, new ViewResolverManager());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}