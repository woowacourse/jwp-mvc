package nextstep.mvc.exception;

public class NextstepMvcException extends RuntimeException {
    private NextstepMvcException(Exception e) {
        super(e);
    }

    public static NextstepMvcException ofException(Exception e) {
        return new NextstepMvcException(e);
    }
}
