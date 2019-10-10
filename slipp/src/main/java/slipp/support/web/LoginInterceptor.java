package slipp.support.web;

import nextstep.mvc.tobe.interceptor.HandlerInterceptor;
import slipp.controller2.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            response.sendRedirect("/users/loginForm");
            return false;
        }
        return true;
    }
}
