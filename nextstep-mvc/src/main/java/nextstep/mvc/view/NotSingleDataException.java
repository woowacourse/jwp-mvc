package nextstep.mvc.view;

class NotSingleDataException extends RuntimeException {
    NotSingleDataException() {
        super("1개의 데이터를 Json 으로 변환 하는중 오류가 생겼습니다.");
    }
}
