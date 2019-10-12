package nextstep.mvc.tobe.controllermapper;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;

public class JavaBeanCreateUtil {
    public static Object getJavaBean(HttpServletRequest request, Class<?> clazz) {
        try {
            Object object = clazz.newInstance();
            setParameter(request, clazz.getDeclaredFields(), object);
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("JavaBean 생성 불가");
        }
    }

    private static void setParameter(HttpServletRequest request, Field[] fields, Object object) throws IllegalAccessException {
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(object, request.getParameter(field.getName()));
        }
    }
}
