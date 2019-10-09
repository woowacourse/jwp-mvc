package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.adapter.AnnotationHandlerAdapter;
import nextstep.mvc.tobe.hadler.AnnotationHandlerMapping;
import nextstep.mvc.tobe.viewresolver.JsonViewResolver;
import nextstep.mvc.tobe.viewresolver.JspViewResolver;
import nextstep.mvc.tobe.viewresolver.RedirectViewResolver;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer  implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        DispatcherServlet dispatcherServlet = new DispatcherServlet(
                Arrays.asList(
                        new AnnotationHandlerMapping("slipp")
                ),
                Arrays.asList(
                        new AnnotationHandlerAdapter()
                ),
                Arrays.asList(
                        new JspViewResolver(),
                        new JsonViewResolver(),
                        new RedirectViewResolver()
                )
        );

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}