package nextstep.mvc;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.resolver.JspViewResolver;
import nextstep.mvc.tobe.resolver.ViewResolver;
import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private static final String VIEW_RESOLVER_BASE_PACKAGE = "nextstep.mvc.tobe.resolver";

    private HandlerMapping[] handlerMappings;
    private List<ViewResolver> viewResolvers;

    public DispatcherServlet(HandlerMapping... handlerMappings) {
        this.handlerMappings = handlerMappings;
        this.viewResolvers = getViewResolvers();
    }

    @Override
    public void init() {
        for (HandlerMapping handlerMapping : handlerMappings) {
            handlerMapping.initialize();
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        HandlerExecution handler = Arrays.stream(handlerMappings)
                .filter(handlerMapping -> Objects.nonNull(handlerMapping.getHandler(req)))
                .findFirst()
                .orElseThrow(ServletException::new)
                .getHandler(req);

        adaptHandler(req, resp, handler);
    }

    private List<ViewResolver> getViewResolvers() {
        Reflections reflections = new Reflections(VIEW_RESOLVER_BASE_PACKAGE);
        Set<Class<? extends ViewResolver>> classes = reflections.getSubTypesOf(ViewResolver.class);

        return classes.stream()
                .map(aClass -> {
                    try {
                        return aClass.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        return new JspViewResolver();
                    }
                })
                .collect(Collectors.toList());
    }

    // TODO HandlerAdapter 클래스로 분리하기
    private void adaptHandler(HttpServletRequest req, HttpServletResponse resp, HandlerExecution handler) throws ServletException {
        try {
            ModelAndView mav = handler.handle(req, resp);

            if (Objects.isNull(mav.getView())) {
                View view = this.viewResolvers.stream()
                        .filter(viewResolver -> Objects.nonNull(viewResolver.resolveViewName(mav.getViewName())))
                        .findFirst()
                        .orElseGet(JspViewResolver::new)
                        .resolveViewName(mav.getViewName());
                mav.setView(view);
            }

            mav.getView().render(mav.getModel(), req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }
}
