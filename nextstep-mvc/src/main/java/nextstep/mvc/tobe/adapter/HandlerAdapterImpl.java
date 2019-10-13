package nextstep.mvc.tobe.adapter;

import nextstep.mvc.tobe.HandlerExecution;
import nextstep.mvc.tobe.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// TODO 클래스 이름을 어떻게 해결을 보자
public class HandlerAdapterImpl implements HandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HandlerAdapterImpl.class);

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse resp, HandlerExecution handler) throws ServletException {
        try {
            return handler.handle(req, resp);
        } catch (Exception e) {
            logger.error("Exception : {}", e.getMessage());
            throw new ServletException(e.getMessage());
        }
    }
}
