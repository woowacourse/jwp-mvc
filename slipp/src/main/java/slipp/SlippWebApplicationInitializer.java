package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.HandlerMapping;
import nextstep.mvc.tobe.AnnotationHandlerMapping;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.ModelAndViewResolver;
import nextstep.mvc.tobe.ViewResolver;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        HandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        HandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("slipp.controller");
        List<HandlerMapping> handlerMappings = Arrays.asList(annotationHandlerMapping, manualHandlerMapping);

        Map<Class, ViewResolver> viewResolvers = new HashMap<>();
        viewResolvers.put(String.class, new StringViewResolver());
        viewResolvers.put(ModelAndView.class, new ModelAndViewResolver());

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, viewResolvers);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}