package nextstep.mvc.tobe.controllermapper.adepter;

import nextstep.mvc.tobe.controllermapper.JavaBeanCreateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import slipp.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserParameterAdapter implements ParameterAdapter {
    private static final Logger log = LoggerFactory.getLogger(UserParameterAdapter.class);
    private Class clazz;

    @Override
    public boolean supports(Class clazz) {
        log.info("clazz : {}, supClass : {}", clazz, User.class);
        this.clazz = clazz;
        return clazz.getCanonicalName().equals(User.class.getCanonicalName());
    }

    @Override
    public Object cast(HttpServletRequest request, HttpServletResponse response, String parameterName) throws Exception {
        return JavaBeanCreateUtil.getJavaBean(request, clazz);
    }

}
