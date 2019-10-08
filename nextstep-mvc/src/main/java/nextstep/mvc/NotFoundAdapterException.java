package nextstep.mvc;

public class NotFoundAdapterException extends RuntimeException {
    private static String MESSAGE = "알맞은 어댑터를 찾을 수 없습니다.";
    public NotFoundAdapterException (){
        super(MESSAGE);
    }
}
