package nextstep.mvc.exception;

public class MappingException extends RuntimeException {
    public MappingException() {
        super("중복된 맵핑입니다.");
    }
}
