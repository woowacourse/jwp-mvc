package nextstep.mvc.tobe.resolver;

import nextstep.mvc.tobe.view.ModelAndView;
import nextstep.mvc.tobe.view.View;

public interface ViewResolver {
    // Object로 하면 view를 결정할 때 필요한 객체만 딱 받을 수 있게 된다는 장점이 있는데,
    // ViewResolver를 사용하는 곳이 DispatcherServlet 밖에 없는 지금 상황에서 Object로 넘겨주면
    // 구현 클래스에서 형변환을 해줘야 하는데 그럴 필요가 있을까?
    boolean supports(ModelAndView mav);

    View resolveViewName(String viewName);
}
