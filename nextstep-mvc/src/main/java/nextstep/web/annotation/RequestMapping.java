package nextstep.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static nextstep.web.annotation.RequestMethod.DELETE;
import static nextstep.web.annotation.RequestMethod.GET;
import static nextstep.web.annotation.RequestMethod.POST;
import static nextstep.web.annotation.RequestMethod.PUT;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";

    RequestMethod[] method() default {GET, POST, PUT, DELETE};
}
