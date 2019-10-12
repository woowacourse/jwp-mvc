package slipp.controller.exception;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException() {
        super("Not Found User");
    }
}
