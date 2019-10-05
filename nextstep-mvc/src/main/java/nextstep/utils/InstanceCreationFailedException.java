package nextstep.utils;

public class InstanceCreationFailedException extends RuntimeException {

    public static final String CREATE_INSTANCE_FAILED_EXCEPTION = "Instance를 생성할 수 없습니다.";

    public InstanceCreationFailedException(Exception e) {
        super(CREATE_INSTANCE_FAILED_EXCEPTION, e);
    }
}
