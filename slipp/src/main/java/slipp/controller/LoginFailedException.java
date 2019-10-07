package slipp.controller;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("로그인 실패");
    }
}
