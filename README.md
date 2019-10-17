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

- [x] 컨트롤러의 메서드가 반드시 ModelAndView 를 리턴하진 않아도 된다. String 을 리턴할수도 있고 Object 를 리턴할 수도 있는데 어떻게 대응할 것인지?
    - AnnotationHandlerMapping 이 value 로 HandlerExecution 을 받는데, 이것의 리턴 타입이 ModelAndView 라서 확장이 불가능. Object 로 바꿔야할듯.

- [x] ViewResolver 클래스를 만들어 관심사 분리
    - [x] HandlerExecutionAdapter 클래스는 HandlerExecution 인터페이스를 HandlerAdapter 인터페이스로
변경하는 어댑터의 역할을 함과 동시에, 컨트롤러 메서드가 리턴한 객체의 타입에 따라 적절한 View 를 생성하는 역할까지 도맡고 있다.
후자의 역할을 ViewResolver 클래스에 위임하자.
    - [x] ModelAndView 의 인스턴스 변수 "view"가 View 뿐만 아니라 컨트롤러 메서드가 리턴한 어떤 타입이든 받을 수 있도록
    Object 로 타입을 변경해준다.
    - [x] DispatcherServlet 에서 ViewResolver 를 사용하도록 로직을 변경한다.

- [x] JsonView 클래스 구현, JsonViewTest 테스트 코드로 검증

- [x] UserAcceptanceTest 통과하도록 UserRestController 추가

- [x] slipp 의 asis 컨트롤러들 tobe 로 변경 (애너테이션 기반)
    - [x] NsWebTestClient 에 로그인 세션 ID 유지하도록 메서드에 set-cookie 추가

- [ ] ArgumentResolver 구현

[ ] DispatcherServlet 테스트 코드 작성 (MockHttpServletRequest, MockHttpServletResponse)
