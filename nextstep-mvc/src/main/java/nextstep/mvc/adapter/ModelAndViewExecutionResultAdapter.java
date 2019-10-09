package nextstep.mvc.adapter;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndViewExecutionResultAdapter implements ExecutionResultAdapter {
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object result) throws Exception {
        return (ModelAndView) result;
    }
}
