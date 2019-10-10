package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerAdapter;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.ViewResolver;
import nextstep.mvc.asis.ControllerHandlerAdapter;
import nextstep.mvc.tobe.adapter.AnnotationHandlerMapping;
import nextstep.mvc.tobe.adapter.HandlerExecutionHandlerAdapter;
import nextstep.mvc.tobe.viewResolver.JsonViewResolver;
import nextstep.mvc.tobe.viewResolver.JspViewResolver;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        List<HandlerMapping> handlerMappings = new ArrayList<>();
        handlerMappings.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerMappings.add(new ManualHandlerMapping());

        List<HandlerAdapter> handlerAdapters = new ArrayList<>();
        handlerAdapters.add(new HandlerExecutionHandlerAdapter());
        handlerAdapters.add(new ControllerHandlerAdapter());

        List<ViewResolver> viewResolvers = new ArrayList<>();
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters, viewResolvers);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}