# 프레임워크 구현
## 진행 방법
* 프레임워크 구현에 대한 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 우아한테크코스 코드리뷰
* [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)


## To do
- [x] Annotation Scan 하는 부분을 AnnotationHandlerMapping 클래스에서 분리, 별도의
객체로 책임을 위임한다.

- [x] Dispatcher Servlet 의 입장에서, ManualHandlerMapping 의 리턴 타입(Controller)과
AnnotationHandlerMapping 의 리턴 타입(HandlerExecution)이 맞지 않아 분기문이 발생한다.
이를 방지하기 위해 ControllerAdaptor 를 만들고, HandlerExecution 을 구현하도록 만든다.
ManualHandlerMapping 은 Controller 가 아니라 ControllerAdaptor 를 리턴한다.

- [x] RequestMethod 를 하나만 받는게 아니라 여러 개 받을 수 있도록 리팩토링
    - method 가 설정되어있지 않으면 모든 메서드 지원 (GET/POST/PUT/DELETE)
    - HandlerKey 는 건드리지 않고 RequestMapping 만 건드린다. 
    - ControllerScanner 에서 RequestMethod[] 를 처리하는 로직을 추가한다.

- [ ] DispatcherServlet 테스트 코드 작성 (MockHttpServletRequest, MockHttpServletResponse)
