package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMappingRepository;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
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
    public void onStartup(ServletContext servletContext) throws ServletException {
        HandlerMappingRepository handlerMappingRepository = new HandlerMappingRepository(Arrays.asList(new ManualHandlerMapping(),
                new AnnotationHandlerMapping("slipp")));

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappingRepository);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}