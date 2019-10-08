# 프레임워크 구현
## 미션 

#### 요구사항 1 - 애노테이션 기반 MVC 프레임워크

- AnnotationHandlerMappingTest 통과시키기
  - @Controller 애노테이션이 설정되어 있는 클래스를 찾은 후 @RequestMapping 설정에 따라 요청 URL과 메소드를 연결

#### 요구사항 2 - 레거시 MVC와 애노테이션 기반 MVC 통합

- 지금까지 사용한 MVC 프레임워크(asis 패키지)와 새롭게 구현한 애노테이션 기반 MVC 프레임워크(tobe 패키지)가 공존이 가능한 구조로 MVC 프레임워크를 통합
- 기존코드 건들이지 말기



#### 미션 목표

- 레거시코드 수정 X
- stream 최대한 많이 쓰기 (연습) 

#### 레거시 수정한 부분

- Controller.class Handler 상속
- ManualHandlerMapping.**getHandler(String)** -> **getHandler(HttpServletRequest)**

## TODO

- [x] 미션 요구사항 1,2 구현
- [x] 리턴타입 ModelAndView, String 둘 다 가능하게 하기
- [x] ComponentScanner
- [x] HandlerAdapter
- [x] Controller 메소드 인자 매핑 (java bean으로 매핑, PathVariable)
     - [x] 역할별로 클래스 분리하기
     - [x] 리팩토링하기
     - [x] 누락된 기능 추가하기 (누락된 매핑 등)
     - [x] 특정 기능들(형변환..) 재사용 가능하게 하기
     - [x] DispatcherServlet 에 적용하기
- [ ] 성능 측정 ServletFilter 구현해보기
- [x] /users/{id} - url 요청 가능하게 하기   
- [ ] handlerMappings, adapters 등.. 포장하기
- [x] 모든 클래스 이름 점검 (ex. HandlerMethodArgumentResolver)
- [ ] ResponseEntity 직접 구현하기
## 추가 미션
- [ ] 다른 템플릿 엔진 지원하기
- [ ] 인터셉터 구현하기

새로운 컨트롤러 (RequestMapping 스캔 기반)은 controller2 패키지에 위치



### 질문

- List<HandlerMapping> 도 포장하는 것이 좋을까? 
- try-catch 는 필요한 부분만 묶는 것이 좋을까? 가독성을 위해서 전체를 묶는 것이 좋을까?