package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.adapter.AnnotationHandlerAdapter;
import nextstep.mvc.adapter.HandlerAdapter;
import nextstep.mvc.handler.AnnotationHandlerMapping;
import nextstep.mvc.resolver.*;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.List;

public class SlippWebApplicationInitializer  implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        List<HandlerMapping> mappings = Arrays.asList(
                new AnnotationHandlerMapping("slipp.controller")
        );
        List<HandlerAdapter> adapters = Arrays.asList(
                new AnnotationHandlerAdapter()
        );
        List<ViewResolver> resolvers = Arrays.asList(
                new DefaultViewResolver(),
                new RedirectViewResolver(),
                new JspViewResolver(),
                new JsonViewResolver()
        );
        DispatcherServlet dispatcherServlet = new DispatcherServlet(mappings, adapters, resolvers);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}