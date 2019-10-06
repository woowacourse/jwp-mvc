package nextstep.mvc;

import nextstep.mvc.exception.ExecutionHandleException;
import nextstep.mvc.tobe.Execution;
import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.view.JspView;
import nextstep.mvc.tobe.view.RedirectView;
import nextstep.mvc.tobe.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecutionHandler {
    private static final String REDIRECT_PREFIX = "redirect:";
    private static final String JSP_SUFFIX = ".jsp";

    public static ModelAndView handle(Execution execution,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        if (execution == null) {
            return new ModelAndView(new JspView("/err/404.jsp"));
        }

        Object result = execution.execute(request, response);
        if (result instanceof String) {
            return new ModelAndView(resolve((String) result));
        }
        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }
        throw new ExecutionHandleException("처리할 수 없는 형태입니다.");
    }

    private static View resolve(String viewName) {
        if (viewName.startsWith(REDIRECT_PREFIX)) {
            return new RedirectView(viewName.substring(REDIRECT_PREFIX.length()));
        }
        if (viewName.endsWith(JSP_SUFFIX)) {
            return new JspView(viewName);
        }
        throw new ExecutionHandleException("해당하는 View가 없습니다.");
    }
}
