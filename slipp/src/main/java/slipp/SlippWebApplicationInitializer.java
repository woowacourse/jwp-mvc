package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.argumentresolver.ArgumentResolver;
import nextstep.mvc.argumentresolver.ArgumentResolvers;
import nextstep.mvc.handleradapter.HandlerAdapter;
import nextstep.mvc.handlermapping.AnnotationHandlerMapping;
import nextstep.mvc.handlermapping.HandlerMapping;
import nextstep.mvc.viewresolver.ViewResolver;
import nextstep.utils.ClassUtils;
import nextstep.web.WebApplicationInitializer;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);
    private static final Reflections reflections = new Reflections("nextstep.mvc");

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        List<ArgumentResolver> argumentResolvers = getAllSubtypesOf(ArgumentResolver.class);
        ArgumentResolvers argumentResolversCollections = new ArgumentResolvers(argumentResolvers);
        List<HandlerAdapter> handlerAdapters = getAllSubtypesOf(HandlerAdapter.class);
        inject(handlerAdapters, argumentResolversCollections);

        List<ViewResolver> viewResolvers = getAllSubtypesOf(ViewResolver.class);
        List<HandlerMapping> handlerMappings = Collections.singletonList(new AnnotationHandlerMapping("slipp"));

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerMappings, handlerAdapters, viewResolvers);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

    private void inject(List<HandlerAdapter> handlerAdapters, ArgumentResolvers argumentResolvers1) {
        handlerAdapters
                .stream()
                .forEach(handlerAdapter -> handlerAdapter.addArugmentResolvers(argumentResolvers1));
    }

    private <T> List<T> getAllSubtypesOf(Class<T> clazz) {
        return reflections.getSubTypesOf(clazz)
                .stream()
                .map(ClassUtils::createInstance)
                .collect(Collectors.toList());
    }
}