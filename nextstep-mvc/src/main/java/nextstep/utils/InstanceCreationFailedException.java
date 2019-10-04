package nextstep.utils;

public class InstanceCreationFailedException extends RuntimeException {
    public InstanceCreationFailedException(ReflectiveOperationException e) {
        super("Instance 생성에 실패했습니다.", e);
    }
}
