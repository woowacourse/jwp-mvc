package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.interceptor.InterceptorRegistry;
import nextstep.mvc.tobe.mapping.AnnotationHandlerMapping;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.support.web.LoginInterceptor;
import slipp.support.web.MeasuringInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        InterceptorRegistry interceptorRegistry = initInterceptorRegistry();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(interceptorRegistry, new AnnotationHandlerMapping("slipp"));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

    private InterceptorRegistry initInterceptorRegistry() {
        final InterceptorRegistry registry = new InterceptorRegistry();

        registry.addInterceptor(new MeasuringInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/users");

        return registry;
    }
}