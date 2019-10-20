package nextstep.mvc.handlermapping;

import nextstep.mvc.tobe.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Clazz {
    public Clazz() {
    }

    public void methodOfWrongReturnType(HttpServletRequest request, HttpServletResponse response) {
    }

    public ModelAndView methodOfWrongParams() {
        return null;
    }

    public ModelAndView correctMethod(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
