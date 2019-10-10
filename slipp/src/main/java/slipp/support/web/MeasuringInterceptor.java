package slipp.support.web;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.interceptor.HandlerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MeasuringInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(MeasuringInterceptor.class);
    private static final String BEGIN_TIME = "beginTime";

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final long currentTime = System.currentTimeMillis();
        request.setAttribute(BEGIN_TIME, currentTime);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        final long currentTime = System.currentTimeMillis();
        final long beginTime = (long) request.getAttribute(BEGIN_TIME);
        final long processedTime = currentTime - beginTime;

        logger.debug("processed time: {} Millis", processedTime);
    }
}
