package slipp;

import nextstep.mvc.tobe.JspView;
import nextstep.mvc.tobe.ViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StringViewResolver implements ViewResolver {

    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp, Object view) throws Exception {
        JspView jspView = new JspView((String) view);
        jspView.render(null, req, resp);
    }
}
