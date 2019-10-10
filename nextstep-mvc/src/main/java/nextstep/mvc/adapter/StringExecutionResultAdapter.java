package nextstep.mvc.adapter;

import nextstep.mvc.ModelAndView;
import nextstep.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringExecutionResultAdapter implements ExecutionResultAdapter {
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object result) throws Exception {
        View view = ViewNameResolver.resolve((String) result);
        return new ModelAndView(view);
    }
}
