package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ClassScanner;
import nextstep.mvc.tobe.argumentresolver.ArgumentResolver;
import nextstep.mvc.tobe.argumentresolver.ArgumentResolvers;
import nextstep.mvc.tobe.handleradapter.HandlerAdapter;
import nextstep.mvc.tobe.handleradapter.HandlerExecutionHandlerAdapter;
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
        ArgumentResolvers argumentResolvers = new ArgumentResolvers(
                ClassScanner.scanSubTypesOf(ArgumentResolver.class, this.getClass().getPackage().getName()));
        List<HandlerMapping> requestMappings = Arrays.asList(new AnnotationHandlerMapping("slipp"));
        List<HandlerAdapter> handlerAdapters = Arrays.asList(new HandlerExecutionHandlerAdapter(argumentResolvers));

        DispatcherServlet dispatcherServlet = new DispatcherServlet(requestMappings, handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}