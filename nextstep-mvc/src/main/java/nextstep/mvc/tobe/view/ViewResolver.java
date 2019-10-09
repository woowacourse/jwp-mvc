package nextstep.mvc.tobe.view;

public interface ViewResolver {
    boolean supports(ModelAndView mv);

    View resolve(ModelAndView mv);
}