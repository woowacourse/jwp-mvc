package nextstep.mvc.tobe.view;

public interface ViewResolver {
    boolean canHandle(ModelAndView modelAndView);

    View resolveView(ModelAndView modelAndView);
}
