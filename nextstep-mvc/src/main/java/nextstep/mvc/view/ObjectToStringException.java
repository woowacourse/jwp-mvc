package nextstep.mvc.view;

class ObjectToStringException extends RuntimeException{
    ObjectToStringException() {
        super("1개의 데이터를 Json 으로 변환 하는중 오류가 생겼습니다.");
    }
}
