package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.tobe.handlerresolver.AnnotationHandlerMapping;
import nextstep.mvc.tobe.handlerresolver.HandlerMappingAdapter;
import nextstep.mvc.tobe.handlerresolver.HandlerResolver;
import nextstep.mvc.tobe.view.viewresolver.JsonViewResolver;
import nextstep.mvc.tobe.view.viewresolver.JspViewResolver;
import nextstep.mvc.tobe.view.viewresolver.RedirectViewResolver;
import nextstep.mvc.tobe.view.viewresolver.ViewResolver;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    private List<HandlerResolver> handlerAdapters = new ArrayList<>();
    private List<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initHandlerAdapterStrategy();
        initViewResolverStrategy();

        DispatcherServlet dispatcherServlet = new DispatcherServlet(handlerAdapters, viewResolvers);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }

    private void initViewResolverStrategy() {
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
        viewResolvers.add(new RedirectViewResolver());
    }

    private void initHandlerAdapterStrategy() {
        handlerAdapters.add(new AnnotationHandlerMapping("slipp.controller"));
        handlerAdapters.add(new HandlerMappingAdapter(new ManualHandlerMapping()));
    }
}