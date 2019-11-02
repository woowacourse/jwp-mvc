package slipp;

import nextstep.mvc.DispatcherServlet;
import nextstep.mvc.asis.Controller;
import nextstep.mvc.handler.handleradapter.ControllerAdaptor;
import nextstep.mvc.handler.handleradapter.HandlerAdapterWrappers;
import nextstep.mvc.handler.handleradapter.HandlerExecutionAdapter;
import nextstep.mvc.handler.handlermapping.*;
import nextstep.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.controller.HomeController;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.Arrays;

public class SlippWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(SlippWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        HandlerMapping mapping = InOrderHandlerMapping.from(Arrays.asList(
                ManualHandlerMapping.builder()
                        .urlAndController("/", new HomeController())
                        .build(),
                new AnnotationHandlerMapping("slipp")));

        mapping.initialize();
        // [review] wrapper 가 정말 필요할까?
        // 현재 필요한 이유는??
        //  - 기존 컨트롤러 역할을 해주는 구현체들이 Handler 의 형태가 아님 (여기선 Handler 가 컨트롤러 역할을 추상화)
        //  - 결국 Handler 는 추상화된 컨트롤러
        //  - 전체적으로 추상화된 컨트롤러를 가지고 일하고 싶다면??
        //      - 즉... mapping 자체에서 Handler 를 바로 반환해준다면... wrapper 가 필요 없음
        DispatcherServlet dispatcherServlet = DispatcherServlet.from(
                mapping,
                HandlerAdapterWrappers.builder()
                        .addWrapper(Controller.class, controller -> ControllerAdaptor.from(controller))
                        .addWrapper(HandlerExecution.class, execution -> HandlerExecutionAdapter.from(execution))
                        .build()
        );

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");


        log.info("Start MyWebApplication Initializer");
    }
}