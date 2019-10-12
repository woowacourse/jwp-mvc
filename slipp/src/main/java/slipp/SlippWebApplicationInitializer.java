package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.mapping.HandlerMapping;
import nextstep.mvc.tobe.adapter.HandlerAdapter;
import nextstep.mvc.tobe.adapter.HandlerExecutionAdapter;
import nextstep.mvc.tobe.mapping.AnnotationHandlerMapping;
import nextstep.mvc.tobe.support.ControllerScanner;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ControllerScanner scanner = createControllerScanner();

        final List<HandlerMapping> handlerMappings = Collections.singletonList(
                new AnnotationHandlerMapping(scanner)
        );

        final List<HandlerAdapter> handlerAdapters = Collections.singletonList(
                new HandlerExecutionAdapter()
        );

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

    private ControllerScanner createControllerScanner() throws ServletException {
        ControllerScanner scanner;
        try {
            scanner = new ControllerScanner("slipp.controller");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            log.error("error {} while ControllerScanner Construct", e.getMessage());
            throw new ServletException();
        }
        return scanner;
    }
}