package nextstep.mvc.adapter.result;

import nextstep.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModelAndViewExecutionResultAdapter extends ExecutionResultAdapter<ModelAndView> {
    public ModelAndViewExecutionResultAdapter() {
        super(ModelAndView.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, ModelAndView result) {
        return result;
    }
}
