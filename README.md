## GIT CONVENTION
https://gist.github.com/joshbuchea/6f47e86d2510bce28f8e7f42ae84c716

## TODO
#### 요구사항 1
- [x] study 모듈 > src/test/java > reflection > ReflectionTest의 showClass() 메소드를 구현해 Question 클래스의 모든 필드, 생성자, 메소드에 대한 정보를 출력한다.
- [x] Question 클래스의 모든 필드 출력
- [x] Question 클래스 생성자 출력
- [x] Question 클래스 모든 메소드 출력

#### 요구사항 2
- [x] Junit3Test 클래스에서 test로 시작하는 메소드만 Java Reflection을 활용해 실행하도록 구현한다.
- [x] test1()
- [x] test2()

#### 요구사항 3
- [x] Junit4에서는 @Test 애노테이션일 설정되어 있는 메소드를 자동으로 실행한다

#### 요구사항 4
- [x] 자바 Reflection API를 활용해 다음 Student 클래스의 name과 age 필드에 값을 할당한 후 getter 메소드를 통해 값을 확인한다.

#### 요구사항 5
- [x] Question 클래스의 인스턴스를 자바 Reflection API를 활용해 Question 인스턴스를 생성한다.

#### 요구사항 6
- [x] examples 패키지에서 @Controller, @Service, @Repository 애노테이션이 설정되어 있는 모든 클래스를 찾아 출력한다.

----
#### 요구사항 1
- @Controller 인 클래스를 찾음
- @RequestMapping의 값에 따라 적절한 HandlerExecution을 반환.
- [x] HandlerExecution 생성자 생성
- [x] AnnotationHandlerMappingTest 성공하게 변경
- [x] ```@RequestMapping``` 에서 method 값이 없을 때 전부 다 매핑되게 변경
- [x] ```@RequestMapping``` 에서 value 값이 없을 때 등록 안되고 에러 로그 찍어주기

#### 요구사항 2
- [x] 몇가지 url (```/users/create```,```/users/login```, ```/users/form```, ```/users``` )일 경우 새로 만든 ```AnnotationHandlerMapping``` 사용하게 변경
- [x] dispatchservlet에서 하나의 handlerMapping만 사용하도록 변경
- [x] view 변경? modelAndView 사용????
- [x] 컨트롤러의 리턴값을 결정 (String vs View vs ModelAndView vs All) -> String, ModelAndView 동시 사용 가능 

---
### 피드백
- [x] ```DispatcherServlet```에 있는 ```getHandlerFromMapping()```에서 하는 ```handler``` 매핑 과정을 좀 더 유연하게 리팩토링 하기(지금은 ```if```로 하고있음.)
- [x] ```DispatcherServlet```에서 ```mapping```되는 ```handler```가 없을 때 나는 에러 생성하기(지금은 그냥 ```Exception``` 던짐)
- [x] ```HandlerExecution```에서 ```view```를 만드는데, ```String```을 가지고 ```view```를 만드는 부분이 겹친다. 한번 중복을 제거해보자.
- [x] ```AnnotationHandlerMapping```에서 컨트롤러를 스캔하는 부분을 다른 클래스로 분리.
- [x] ```nextstep-mvc.src.test.java.nextstep.mvc.tobe```에 있는 실제 테스트 클래스 이외의 학습을 위한 테스트들은 따로 패키지 분리하기
- [x] ```DispatcherServlet``` 생성자에서 ```HandlerMapping```를 리스트로 받아서 ```foreach```하며 ```init()```하기
- [x] ```handlerAdapter``` 리팩토링
- [x] ```AnnotationHandlerMapping``` 리팩토링 (한줄에 점 하나만 찍기)
- [x] ```Handler```의 ```handle()```의 리턴값을 ```Object```에서 ```ModelAndView```로 바꾸기(```DispatcherServlet```에서는 ```view```를 모르게 하기)

---
### 2단계 요구사항
- [x] JsonView.render 구현 (JsonViewTest 성공하게 바꾸기)
- [x] post의 body값 읽어오는 controller 생성 (UserAcceptanceTest 성공하게 변경)
- [ ] 모든 Controller를 애노테이션 기반 MVC(@MVC)로 변경한 후 asis MVC 프레임워크 관련 코드를 모두 삭제