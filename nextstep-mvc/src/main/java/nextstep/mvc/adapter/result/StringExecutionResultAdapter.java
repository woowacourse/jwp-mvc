package nextstep.mvc.adapter.result;

import nextstep.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringExecutionResultAdapter extends ExecutionResultAdapter<String> {
    public StringExecutionResultAdapter() {
        super(String.class);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, String result) {
        return new ModelAndView(ViewNameResolver.resolve(result));
    }
}
