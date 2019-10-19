package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.AnnotatedHandlerAdapter;
import nextstep.mvc.tobe.AnnotatedHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer  implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        final ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "dispatcher",
                new DispatcherServlet(
                        Arrays.asList(new AnnotatedHandlerMapping("slipp")),
                        Arrays.asList(new AnnotatedHandlerAdapter())
                )
        );
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        logger.info("Start MyWebApplication Initializer");
    }
}